package com.example.ProyectoFinalAplicacionesWeb.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VarianteProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String plataforma;

    @Column(precision = 12, scale = 2)
    private BigDecimal precio;

    private int stock;

    private boolean activo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "producto_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Producto producto;

    private String soporteFisico;
    private String region;
    private String dimensiones;

    // VarianteProducto 0..* <--> 1..* Idioma
    @ManyToMany
    @JoinTable(
            name = "variante_producto_idioma",
            joinColumns = @JoinColumn(name = "variante_producto_id"),
            inverseJoinColumns = @JoinColumn(name = "idioma_id")
    )
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Idioma> idiomas = new ArrayList<>();
}
