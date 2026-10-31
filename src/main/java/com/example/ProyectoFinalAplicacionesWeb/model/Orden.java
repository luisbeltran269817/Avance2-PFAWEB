package com.example.ProyectoFinalAplicacionesWeb.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Orden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String folio;

    private LocalDateTime fecha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoOrden estadoOrden;

    //digitos = 12, despues del punto decimal = 2
    //o sea el numero mas grande que podriamos tener es 9999999999.99
    @Column(precision = 12, scale = 2)
    private BigDecimal total;

    //cliente 1 -> 0..* Orden
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Cliente cliente;

    //dirección registrada del cliente (1 Direccion -> * Orden)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "direccion_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Direccion direccion;

    //orden 1 -> 1..* DetalleOrden
    @OneToMany(mappedBy = "orden", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<DetalleOrden> detalles = new ArrayList<>();

    //igual se guarda la direccion al momento de compra por si se llega modificar despues
    private String calle;
    private String numero;
    private String ciudad;
    private String estado;
    private String codigoPostal;

    //orden 1 -> 1 Envio
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "envio_id", unique = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Envio envio;

    //orden 1 -> 1 Pago
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "pago_id", unique = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Pago pago;
}
