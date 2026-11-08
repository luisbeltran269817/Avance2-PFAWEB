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
public class VarianteFormDTO {
    private Long productoId;
    private String plataforma;
    private BigDecimal precio;
    private int stock;
    private String dimensiones;
    private String region;
    private String soporteFisico;
    private boolean activo = true;
    private List<Long> idiomaIds = new ArrayList<>();
}
