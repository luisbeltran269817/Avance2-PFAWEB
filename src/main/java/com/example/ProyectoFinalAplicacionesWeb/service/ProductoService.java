package com.example.ProyectoFinalAplicacionesWeb.service;

import com.example.ProyectoFinalAplicacionesWeb.dto.*;
import com.example.ProyectoFinalAplicacionesWeb.model.*;
import com.example.ProyectoFinalAplicacionesWeb.repository.CategoriaRepository;
import com.example.ProyectoFinalAplicacionesWeb.repository.ProductoRepository;

import com.example.ProyectoFinalAplicacionesWeb.repository.VarianteProductoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class ProductoService {

    @Autowired
    ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private VarianteProductoRepository varianteProductoRepository;

    /**
     * consigue todos los productos con repository, despues los convierte a dto
     * @return lista de todos los productos como DTO
     *
     * NOTA: este no lo termine usando para la pantalla, pero lo dejare porque puede
     * ser util en alguna otra pantalla que se necesiten mostrar todos los productos
     */
    public List<ProductoDTO> listarProductos() {
        return productoRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    /**
     * metodo para buscar productos por nombre
     * @param nombre
     * @return lista de los productos que su nombre contiene el parametro
     */
    public List<ProductoDTO> buscarProductos(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            return listarProductos();
        }
        return productoRepository.findByNombreContainingIgnoreCase(nombre.trim()).stream()
                .map(this::convertirADTO)
                .toList();
    }

    @Transactional
    public Producto guardarProducto(ProductoFormDTO form) {
        if (form.getCategoriaId() == null) {
            throw new IllegalArgumentException("Debe estar una categoria");
        }
        Categoria categoria = categoriaRepository.findById(form.getCategoriaId()).orElseThrow(() -> new IllegalArgumentException("La categoría seleccionada no existe"));

        //si una tarjeta de imagen no venia con url se descarta
        List<ImagenProductoDTO> validas = form.getImagenes().stream()
                .filter(i -> i.getUrl() != null && !i.getUrl().isBlank())
                .toList();

        if (validas.isEmpty()) {
            throw new IllegalArgumentException("El producto debe tener al menos una imagen");
        }

        //solo debe haber una imagen con principal true
        //se deja la primera marcada de la lista o si no hay marcada se marca la primera de la lista
        int indicePrincipal = 0;
        for (int i = 0; i < validas.size(); i++) {
            if (Boolean.TRUE.equals(validas.get(i).getPrincipal())) {
                indicePrincipal = i;
                break;
            }
        }

        List<ImagenProducto> imagenes = new ArrayList<>();
        for (int i = 0; i < validas.size(); i++) {
            ImagenProductoDTO dto = validas.get(i);
            imagenes.add(ImagenProducto.builder()
                    .url(dto.getUrl().trim())
                    .orden(dto.getOrden() != null ? dto.getOrden() : i + 1)
                    .principal(i == indicePrincipal)
                    .build());
        }

        Producto producto = Producto.builder()
                .nombre(form.getNombre().trim())
                .descripcion(form.getDescripcion())
                .categoria(categoria)
                .imagenes(imagenes)
                .build();
        
        return productoRepository.save(producto);
    }

    //metodo auxiliar que convierte un producto en productodto
    private ProductoDTO convertirADTO(Producto producto) {
        List<ImagenProductoDTO> imagenes = producto.getImagenes().stream()
                .sorted(Comparator.comparingInt(ImagenProducto::getOrden))
                .map(this::convertirImagenADTO)
                .toList();

        List<VarianteProductoDTO> variantes = producto.getVariantes().stream()
                .map(this::convertirVarianteADTO)
                .toList();

        ProductoDTO dto = new ProductoDTO();
        dto.setId(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripcion());
        dto.setCategoria(convertirCategoriaADTO(producto.getCategoria()));
        dto.setImagenes(imagenes);
        dto.setVariantes(variantes);
        return dto;
    }

    //igual aqui, auxiliar para imagen a imagendto
    private ImagenProductoDTO convertirImagenADTO(ImagenProducto imagen) {
        return new ImagenProductoDTO(
                imagen.getId(),
                imagen.getUrl(),
                imagen.getOrden(),
                imagen.isPrincipal()
        );
    }

    //y otro auxiliar para categoria
    private CategoriaDTO convertirCategoriaADTO(Categoria categoria) {
        if (categoria == null) {
            return null;
        }
        return new CategoriaDTO(
                categoria.getId(),
                categoria.getNombre(),
                categoria.getDescripcion()
        );
    }

    //y otro auxiliar para las variantes...
    private VarianteProductoDTO convertirVarianteADTO(VarianteProducto variante) {
        List<IdiomaDTO> idiomas = variante.getIdiomas().stream()
                .map(this::convertirIdiomaADTO)
                .toList();

        VarianteProductoDTO dto = new VarianteProductoDTO();
        dto.setId(variante.getId());
        dto.setActivo(variante.isActivo());
        dto.setDimensiones(variante.getDimensiones());
        dto.setPlataforma(variante.getPlataforma());
        dto.setPrecio(variante.getPrecio());
        dto.setRegion(variante.getRegion());
        dto.setSoporteFisico(variante.getSoporteFisico());
        dto.setStock(variante.getStock());
        dto.setIdiomas(idiomas);
        return dto;
    }

    //y otro auxiliar para los idiomas de las variantes........ ;-;
    private IdiomaDTO convertirIdiomaADTO(Idioma idioma) {
        return new IdiomaDTO(idioma.getId(), idioma.getNombre());
    }

    @Transactional
    public void actualizarProducto(Long id, ProductoEdicionDTO productoEdicionDTO) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El producto no existe"));
        Categoria categoria = categoriaRepository.findById(productoEdicionDTO.getCategoriaId())
                .orElseThrow(() -> new IllegalArgumentException("La categoría seleccionada no existe"));
        VarianteProducto variante = varianteProductoRepository.findById(productoEdicionDTO.getVarianteId())
                .orElseThrow(() -> new IllegalArgumentException("La variante no existe"));

        //la variante tiene que ser de este producto
        if (!variante.getProducto().getId().equals(id)) {
            throw new IllegalArgumentException("La variante no pertenece al producto");
        }

        producto.setNombre(productoEdicionDTO.getNombre().trim());
        producto.setDescripcion(productoEdicionDTO.getDescripcion());
        producto.setCategoria(categoria);

        //solo queda como principal la imagen que eligio el radio
        for (int i = 0; i < productoEdicionDTO.getImagenes().size(); i++) {
            ImagenProductoDTO dto = productoEdicionDTO.getImagenes().get(i);
            for (ImagenProducto imagen : producto.getImagenes()) {
                if (imagen.getId().equals(dto.getId())) {
                    imagen.setUrl(dto.getUrl().trim());
                    imagen.setOrden(dto.getOrden() != null ? dto.getOrden() : i + 1);
                    imagen.setPrincipal(i == productoEdicionDTO.getImagenPrincipal());
                }
            }
        }

        variante.setPrecio(productoEdicionDTO.getPrecio());
    }

    public ProductoEdicionDTO buscarVarianteProductoPorId(Long id) {
        VarianteProducto variante = varianteProductoRepository.findById(id).orElse(null);
        if (variante == null) {
            return null;
        }
        return convertirAEdicionDTO(variante.getProducto(), variante);
    }

    //auxiliar que convierte un producto y una variante en el dto del formulario de editar
    private ProductoEdicionDTO convertirAEdicionDTO(Producto producto, VarianteProducto variante) {
        List<ImagenProductoDTO> imagenes = new ArrayList<>(producto.getImagenes().stream()
                .sorted(Comparator.comparingInt(ImagenProducto::getOrden))
                .map(this::convertirImagenADTO)
                .toList());

        //se busca en que posicion de la lista esta la imagen principal
        int indicePrincipal = 0;
        for (int i = 0; i < imagenes.size(); i++) {
            if (Boolean.TRUE.equals(imagenes.get(i).getPrincipal())) {
                indicePrincipal = i;
                break;
            }
        }

        ProductoEdicionDTO dto = new ProductoEdicionDTO();
        dto.setId(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripcion());
        dto.setCategoriaId(producto.getCategoria() != null ? producto.getCategoria().getId() : null);
        dto.setImagenPrincipal(indicePrincipal);
        dto.setImagenes(imagenes);
        dto.setVarianteId(variante.getId());
        dto.setPlataforma(variante.getPlataforma());
        dto.setPrecio(variante.getPrecio());
        return dto;
    }
}
