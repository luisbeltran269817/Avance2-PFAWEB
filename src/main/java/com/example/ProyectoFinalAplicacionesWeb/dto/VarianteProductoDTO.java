package com.example.ProyectoFinalAplicacionesWeb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VarianteProductoDTO {
    private Long id;
    private boolean activo;//
    private String dimensiones;//
    private String plataforma;//
    private BigDecimal precio;//
    private String region;//
    private String soporteFisico;//
    private int stock;//
    private List<IdiomaDTO> idiomas;

}
