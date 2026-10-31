package com.example.ProyectoFinalAplicacionesWeb.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AjusteInventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String motivo;

    private LocalDateTime fecha;

    private int cantidad;

    //varianteProducto 1 -> * AjusteInventario
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "variante_producto_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private VarianteProducto varianteProducto;

    //admin 1 -> * AjusteInventario
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "administrador_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Administrador administrador;
}
