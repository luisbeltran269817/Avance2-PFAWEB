package com.example.ProyectoFinalAplicacionesWeb.repository;

import com.example.ProyectoFinalAplicacionesWeb.model.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministradorRepository extends JpaRepository<Administrador, Long> {
}
