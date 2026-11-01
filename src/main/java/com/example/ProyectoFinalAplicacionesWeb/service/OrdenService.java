package com.example.ProyectoFinalAplicacionesWeb.service;

import com.example.ProyectoFinalAplicacionesWeb.dto.PedidoDTO;
import com.example.ProyectoFinalAplicacionesWeb.model.DetalleOrden;
import com.example.ProyectoFinalAplicacionesWeb.model.Orden;
import com.example.ProyectoFinalAplicacionesWeb.repository.OrdenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrdenService {


    @Autowired
    private OrdenRepository ordenRepository;

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private PedidoDTO convertirADTO(Orden orden) {
        List<DetalleOrden> detalles = orden.getDetalles();

        int unidades = 0;
        for (DetalleOrden detalle : detalles) {
            unidades += detalle.getCantidad();
        }

        String producto = "Sin productos";
        if (!detalles.isEmpty()) {
            producto = detalles.get(0).getProducto().getProducto().getNombre();
            if (detalles.size() > 1) {
                producto = producto + " y " + (detalles.size() - 1) + " más";
            }
        }

        String fecha = "";
        if (orden.getFecha() != null) {
            fecha = orden.getFecha().format(FORMATO_FECHA);
        }

        return new PedidoDTO(
                orden.getId(),
                orden.getFolio(),
                fecha,
                orden.getEstadoOrden().name().toLowerCase(),
                orden.getCliente().getNombre(),
                orden.getCliente().getCorreo(),
                producto,
                unidades,
                orden.getTotal()
        );
    }

    // lista todos los pedidos en DTO, de nuevo a viejo
    public List<PedidoDTO> listarPedidos() {
        List<PedidoDTO> pedidos = new ArrayList<>();
        for (Orden orden : ordenRepository.findAllByOrderByFechaDesc()) {
            pedidos.add(convertirADTO(orden));
        }
        return pedidos;
    }

}
