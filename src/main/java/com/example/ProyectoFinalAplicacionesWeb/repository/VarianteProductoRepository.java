package com.example.ProyectoFinalAplicacionesWeb.repository;

import com.example.ProyectoFinalAplicacionesWeb.model.VarianteProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VarianteProductoRepository extends JpaRepository<VarianteProducto, Long> {

}
