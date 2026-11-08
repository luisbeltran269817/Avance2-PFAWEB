package com.example.ProyectoFinalAplicacionesWeb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResumenPedidosDTO {

    private long total;
    private long pendientes;
    private long procesando;
    private BigDecimal ventasMes;
}
