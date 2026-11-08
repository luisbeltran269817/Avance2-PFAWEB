package com.example.ProyectoFinalAplicacionesWeb.repository;

import com.example.ProyectoFinalAplicacionesWeb.model.Orden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.example.ProyectoFinalAplicacionesWeb.model.EstadoOrden;
import java.time.LocalDateTime;

@Repository
public interface OrdenRepository extends JpaRepository<Orden, Long> {

    //metodo derivado
    List<Orden> findAllByOrderByFechaDesc();

    //cuenta ordenes con el mismo estado
    long countByEstadoOrden(EstadoOrden estadoOrden);

    //este metodo derivado hace
    // que la fecha sea mayor o igual a la del argumento y
    // que sea un estado diferente al argumento
    List<Orden> findByFechaGreaterThanEqualAndEstadoOrdenNot(LocalDateTime inicio, EstadoOrden estadoOrden);
}
