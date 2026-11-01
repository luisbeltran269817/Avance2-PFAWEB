package com.example.ProyectoFinalAplicacionesWeb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {

    private Long id;
    private String folio;
    private String fecha;
    private String estado;
    private String cliente;
    private String correo;
    private String producto;
    private int unidades;
    private BigDecimal total;
}
