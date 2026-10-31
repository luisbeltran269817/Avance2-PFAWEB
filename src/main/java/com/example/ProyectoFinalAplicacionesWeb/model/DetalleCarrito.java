package com.example.ProyectoFinalAplicacionesWeb.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleCarrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int cantidad;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "carrito_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Carrito carrito;

    //detalleCarrito 0..* -> 1 VarianteProducto
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "variante_producto_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private VarianteProducto producto;
}
