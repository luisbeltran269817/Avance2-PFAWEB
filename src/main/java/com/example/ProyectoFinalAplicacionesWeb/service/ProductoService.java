package com.example.ProyectoFinalAplicacionesWeb.service;

import com.example.ProyectoFinalAplicacionesWeb.dto.CategoriaDTO;
import com.example.ProyectoFinalAplicacionesWeb.dto.ImagenProductoDTO;
import com.example.ProyectoFinalAplicacionesWeb.dto.ProductoDTO;
import com.example.ProyectoFinalAplicacionesWeb.model.Categoria;
import com.example.ProyectoFinalAplicacionesWeb.model.ImagenProducto;
import com.example.ProyectoFinalAplicacionesWeb.model.Producto;
import com.example.ProyectoFinalAplicacionesWeb.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class ProductoService {

    @Autowired
    ProductoRepository productoRepository;

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

    //metodo auxiliar que convierte un producto en productodto
    private ProductoDTO convertirADTO(Producto producto) {
        List<ImagenProductoDTO> imagenes = producto.getImagenes().stream()
                .sorted(Comparator.comparingInt(ImagenProducto::getOrden))
                .map(this::convertirImagenADTO)
                .toList();

        ProductoDTO dto = new ProductoDTO();
        dto.setId(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripcion());
        dto.setCategoria(convertirCategoriaADTO(producto.getCategoria()));
        dto.setImagenes(imagenes);
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
}
