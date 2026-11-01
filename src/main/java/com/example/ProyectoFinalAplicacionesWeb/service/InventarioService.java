package com.example.ProyectoFinalAplicacionesWeb.service;

import com.example.ProyectoFinalAplicacionesWeb.model.VarianteProducto;
import com.example.ProyectoFinalAplicacionesWeb.repository.VarianteProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.example.ProyectoFinalAplicacionesWeb.dto.VarianteInventarioDTO;
import java.util.ArrayList;

@Service
public class InventarioService {

    // inyección de repository
    @Autowired
    private VarianteProductoRepository varianteProductoRepository;

    // limites de stock, crítico 0 a 3, bajo 4 a 10, normal 11 o mas
    private static final int STOCK_CRITICO = 3;
    private static final int STOCK_BAJO = 10;

    public List<VarianteProducto> listarVariantes() {
        return varianteProductoRepository.findAll();
    }

    public String calcularEstadoStock(int stock) {
        if (stock <= STOCK_CRITICO) {
            return "critico";
        }
        if (stock <= STOCK_BAJO) {
            return "bajo";
        }
        return "normal";
    }

    // convierte cada variante en DTO con el estado de stock ya calculado
    public List<VarianteInventarioDTO> listarInventario() {
        List<VarianteInventarioDTO> inventario = new ArrayList<>();
        for (VarianteProducto variante : listarVariantes()) {
            inventario.add(new VarianteInventarioDTO(
                    variante.getId(),
                    variante.getProducto().getNombre(),
                    variante.getPlataforma(),
                    variante.getStock(),
                    variante.isActivo(),
                    calcularEstadoStock(variante.getStock())
            ));
        }
        return inventario;
    }
}