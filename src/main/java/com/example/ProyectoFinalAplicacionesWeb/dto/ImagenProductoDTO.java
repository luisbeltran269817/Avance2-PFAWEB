package com.example.ProyectoFinalAplicacionesWeb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImagenProductoDTO {
    private Long id;
    private String url;
    private int orden;
    private Boolean principal;
}
