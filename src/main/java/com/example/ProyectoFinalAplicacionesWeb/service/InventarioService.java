package com.example.ProyectoFinalAplicacionesWeb.service;

import com.example.ProyectoFinalAplicacionesWeb.dto.ResumenInventarioDTO;
import com.example.ProyectoFinalAplicacionesWeb.model.VarianteProducto;
import com.example.ProyectoFinalAplicacionesWeb.repository.VarianteProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.example.ProyectoFinalAplicacionesWeb.dto.VarianteInventarioDTO;
import java.util.ArrayList;
import com.example.ProyectoFinalAplicacionesWeb.model.Administrador;
import com.example.ProyectoFinalAplicacionesWeb.model.AjusteInventario;
import com.example.ProyectoFinalAplicacionesWeb.repository.AdministradorRepository;
import com.example.ProyectoFinalAplicacionesWeb.repository.AjusteInventarioRepository;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
public class InventarioService {

    // inyección de repository
    @Autowired
    private VarianteProductoRepository varianteProductoRepository;

    @Autowired
    private AjusteInventarioRepository ajusteInventarioRepository;

    @Autowired
    private AdministradorRepository administradorRepository;

    // limites de stock, crítico 0 a 3, bajo 4 a 10, normal 11 o mas
    private static final int STOCK_CRITICO = 3;
    private static final int STOCK_BAJO = 10;

    //id mock, en la bd inserté a un admin tambien, esto es temporal hasta que esté listo el login
    private static final Long ID_ADMIN_TEMPORAL = 1L;

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

    // convierte una variante en DTO con el estado de stock ya calculado
    private VarianteInventarioDTO convertirADTO(VarianteProducto variante) {
        return new VarianteInventarioDTO(
                variante.getId(),
                variante.getProducto().getNombre(),
                variante.getPlataforma(),
                variante.getStock(),
                variante.isActivo(),
                calcularEstadoStock(variante.getStock())
        );
    }

    // lista el inventario como DTO para el dashboard
    public List<VarianteInventarioDTO> listarInventario() {
        List<VarianteInventarioDTO> inventario = new ArrayList<>();
        for (VarianteProducto variante : listarVariantes()) {
            inventario.add(convertirADTO(variante));
        }
        return inventario;
    }

    // cuenta cuántas variantes hay en cada estado de stock
    public ResumenInventarioDTO calcularResumen(List<VarianteInventarioDTO> inventario) {
        int criticos = 0;
        int bajos = 0;
        int normales = 0;

        for (VarianteInventarioDTO item : inventario) {
            switch (item.getEstado()) {
                case "critico":
                    criticos++;
                    break;
                case "bajo":
                    bajos++;
                    break;
                default:
                    normales++;
                    break;
            }
        }
        return new ResumenInventarioDTO(inventario.size(), criticos, bajos, normales);
    }

    // busca variante por id para mostrarla en la pantalla de ajuste
    public VarianteInventarioDTO obtenerVariante(Long id) {
        VarianteProducto variante = varianteProductoRepository.findById(id).orElse(null);
        if (variante == null) {
            return null;
        }
        return convertirADTO(variante);
    }

    //modifica la cantidad de stock y persiste el dato
    @Transactional
    public boolean registrarAjuste(Long idVariante, int cantidad, String motivo) {
        VarianteProducto variante = varianteProductoRepository.findById(idVariante).orElse(null);
        Administrador administrador = administradorRepository.findById(ID_ADMIN_TEMPORAL).orElse(null);

        if (variante == null || administrador == null) {
            return false;
        }
        if (cantidad == 0 || motivo == null || motivo.isBlank()) {
            return false;
        }

        int nuevoStock = variante.getStock() + cantidad;
        if (nuevoStock < 0) {
            return false;
        }

        variante.setStock(nuevoStock);
        varianteProductoRepository.save(variante);

        AjusteInventario ajuste = new AjusteInventario();
        ajuste.setVarianteProducto(variante);
        ajuste.setAdministrador(administrador);
        ajuste.setCantidad(cantidad);
        ajuste.setMotivo(motivo);
        ajuste.setFecha(LocalDateTime.now());
        ajusteInventarioRepository.save(ajuste);

        return true;
    }

}