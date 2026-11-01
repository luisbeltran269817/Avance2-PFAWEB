package com.example.ProyectoFinalAplicacionesWeb.service;

import com.example.ProyectoFinalAplicacionesWeb.dto.CategoriaDTO;
import com.example.ProyectoFinalAplicacionesWeb.dto.ImagenProductoDTO;
import com.example.ProyectoFinalAplicacionesWeb.dto.ProductoDTO;
import com.example.ProyectoFinalAplicacionesWeb.model.Categoria;
import com.example.ProyectoFinalAplicacionesWeb.model.ImagenProducto;
import com.example.ProyectoFinalAplicacionesWeb.model.Producto;
import com.example.ProyectoFinalAplicacionesWeb.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

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

    //auxiliar para convertir a dto
    private CategoriaDTO convertirADTO(Categoria categoria) {
        CategoriaDTO dto = new CategoriaDTO();
        dto.setId(categoria.getId());
        dto.setNombre(categoria.getNombre());
        dto.setDescripcion(categoria.getDescripcion());
        return dto;
    }
}
