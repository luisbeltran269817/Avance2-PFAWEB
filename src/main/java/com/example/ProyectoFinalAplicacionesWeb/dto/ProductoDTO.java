package com.example.ProyectoFinalAplicacionesWeb.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private CategoriaDTO categoria;
    //para esta primera pantalla aun no ocupo estos atributos (hacerlos dto, no como entidad)
    //private List<VarianteProducto> variantes;
    private List<ImagenProductoDTO> imagenes;
}
