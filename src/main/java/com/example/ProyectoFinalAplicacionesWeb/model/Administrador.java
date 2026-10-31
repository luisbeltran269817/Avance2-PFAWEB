package com.example.ProyectoFinalAplicacionesWeb.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Administrador extends Usuario {

    //admin 1 -> 1..* AjusteInventario
    @OneToMany(mappedBy = "administrador")
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<AjusteInventario> ajustes = new ArrayList<>();
}
