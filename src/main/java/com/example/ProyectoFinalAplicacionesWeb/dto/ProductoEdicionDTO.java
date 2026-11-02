package com.example.ProyectoFinalAplicacionesWeb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoEdicionDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private Long categoriaId;

    //posicion en la lista de la imagen que esta marcada como principal
    private int imagenPrincipal;

    private List<ImagenProductoDTO> imagenes = new ArrayList<>();

    //variante que se edita, de esta solo se cambia el precio
    private Long varianteId;
    private String plataforma;
    private BigDecimal precio;
}
