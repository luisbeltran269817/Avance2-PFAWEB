package com.example.ProyectoFinalAplicacionesWeb.service;

import com.example.ProyectoFinalAplicacionesWeb.dto.CategoriaDTO;
import com.example.ProyectoFinalAplicacionesWeb.dto.CategoriasFormDTO;
import com.example.ProyectoFinalAplicacionesWeb.model.Categoria;
import com.example.ProyectoFinalAplicacionesWeb.repository.CategoriaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CategoriaService {

    @Autowired
    CategoriaRepository categoriaRepository;

    /**
     * metodo para conseguir todas las categorias como dto
     * @return lista de categorias como dto
     */
    public List<CategoriaDTO> listarCategorias(){
        return categoriaRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    /**
     * guarda los cambios de la pantalla editar categorias: actualiza las que ya existen
     * y crea la nueva si el admin la lleno. todo en una transaccion, si algo falla no se guarda nada
     * @param form categorias editadas y, opcionalmente, la nueva
     * @throws IllegalArgumentException si una categoria ya no existe, no tiene nombre o el nombre se repite
     */
    @Transactional
    public void guardarCambios(CategoriasFormDTO form) {
        List<Categoria> categorias = new ArrayList<>(categoriaRepository.findAll());
        Map<Long, Categoria> porId = categorias.stream()
                .collect(Collectors.toMap(Categoria::getId, Function.identity()));

        for (CategoriaDTO dto : form.getCategorias()) {
            Categoria categoria = porId.get(dto.getId());
            if (categoria == null) {
                throw new IllegalArgumentException("Una de las categorías ya no existe, recargue la página");
            }
            categoria.setNombre(limpiarNombre(dto.getNombre()));
            categoria.setDescripcion(limpiarDescripcion(dto.getDescripcion()));
        }

        //la tarjeta de nueva categoria solo cuenta si se escribio un nombre
        Categoria nueva = null;
        CategoriaDTO dtoNueva = form.getNueva();
        if (dtoNueva != null && dtoNueva.getNombre() != null && !dtoNueva.getNombre().isBlank()) {
            nueva = Categoria.builder()
                    .nombre(limpiarNombre(dtoNueva.getNombre()))
                    .descripcion(limpiarDescripcion(dtoNueva.getDescripcion()))
                    .build();
            categorias.add(nueva);
        }

        //se valida con los nombres ya finales (cambios y nueva incluidos)
        validarNombresUnicos(categorias);

        if (nueva != null) {
            categoriaRepository.save(nueva);
        }
        //las categorias editadas se guardan solas al terminar la transaccion
    }

    /**
     * elimina una categoria, solo si no tiene productos
     * @throws IllegalArgumentException si no existe o todavia tiene productos
     */
    @Transactional
    public void eliminarCategoria(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("La categoría no existe"));
        int cantidad = categoria.getProductos().size();
        if (cantidad > 0) {
            throw new IllegalArgumentException("No se puede eliminar \"" + categoria.getNombre()
                    + "\" porque tiene " + cantidad + " producto(s). Cambie esos productos de categoría primero");
        }
        categoriaRepository.delete(categoria);
    }

    //auxiliar: el nombre es obligatorio
    private String limpiarNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre de la categoría no puede estar vacío");
        }
        return nombre.trim();
    }

    //auxiliar: la descripcion es opcional, vacia se guarda como null
    private String limpiarDescripcion(String descripcion) {
        if (descripcion == null || descripcion.isBlank()) {
            return null;
        }
        return descripcion.trim();
    }

    //auxiliar: no puede haber dos categorias con el mismo nombre (sin importar mayusculas)
    private void validarNombresUnicos(List<Categoria> categorias) {
        Set<String> vistos = new HashSet<>();
        for (Categoria categoria : categorias) {
            if (!vistos.add(categoria.getNombre().toLowerCase())) {
                throw new IllegalArgumentException("Ya existe una categoría llamada \"" + categoria.getNombre() + "\"");
            }
        }
    }

    //auxiliar para convertir a dto
    private CategoriaDTO convertirADTO(Categoria categoria) {
        CategoriaDTO dto = new CategoriaDTO();
        dto.setId(categoria.getId());
        dto.setNombre(categoria.getNombre());
        dto.setDescripcion(categoria.getDescripcion());
        return dto;
    }
}

