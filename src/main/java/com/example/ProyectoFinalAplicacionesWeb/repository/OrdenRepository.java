package com.example.ProyectoFinalAplicacionesWeb.repository;

import com.example.ProyectoFinalAplicacionesWeb.model.Orden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrdenRepository extends JpaRepository<Orden, Long> {

    //metodo derivado
    List<Orden> findAllByOrderByFechaDesc();
}
