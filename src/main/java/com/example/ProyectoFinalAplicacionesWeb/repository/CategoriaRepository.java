package com.example.ProyectoFinalAplicacionesWeb.repository;

import com.example.ProyectoFinalAplicacionesWeb.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
