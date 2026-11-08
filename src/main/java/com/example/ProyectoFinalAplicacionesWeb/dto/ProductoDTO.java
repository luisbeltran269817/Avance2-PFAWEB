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
    private List<VarianteProductoDTO> variantes;
    private List<ImagenProductoDTO> imagenes;
}
