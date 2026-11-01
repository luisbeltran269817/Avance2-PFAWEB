package com.example.ProyectoFinalAplicacionesWeb.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

//si alguien ocupa un dto de producto, NO AGARREN ESTE
//este es para el formulario de añadir producto
//si ocupan un dto de producto, vayan a ProductoDTO
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoFormDTO {
    private String nombre;
    private String descripcion;
    private Long categoriaId;

    //esto inicializa una lista de imagenes vacia para el dto
    @Builder.Default
    private List<ImagenProductoDTO> imagenes = new ArrayList<>();
}
