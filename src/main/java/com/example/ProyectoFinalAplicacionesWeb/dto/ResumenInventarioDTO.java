package com.example.ProyectoFinalAplicacionesWeb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResumenInventarioDTO {
    private int total;
    private int criticos;
    private int bajos;
    private int normales;
}
