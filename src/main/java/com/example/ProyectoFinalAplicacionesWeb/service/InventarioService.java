package com.example.ProyectoFinalAplicacionesWeb.service;

import com.example.ProyectoFinalAplicacionesWeb.model.VarianteProducto;
import com.example.ProyectoFinalAplicacionesWeb.repository.VarianteProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class InventarioService {

    // inyección de repository
    @Autowired
    private VarianteProductoRepository varianteProductoRepository;

    // ahorita solo regresa la lista pero después debe de calcular el stock y guardar AjusteInventario
    public List<VarianteProducto> listarVariantes() {
        return varianteProductoRepository.findAll();
    }
}