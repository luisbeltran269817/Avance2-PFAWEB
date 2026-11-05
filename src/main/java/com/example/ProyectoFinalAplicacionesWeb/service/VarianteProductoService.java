package com.example.ProyectoFinalAplicacionesWeb.service;

import com.example.ProyectoFinalAplicacionesWeb.dto.VarianteFormDTO;
import com.example.ProyectoFinalAplicacionesWeb.model.Idioma;
import com.example.ProyectoFinalAplicacionesWeb.model.Producto;
import com.example.ProyectoFinalAplicacionesWeb.model.VarianteProducto;
import com.example.ProyectoFinalAplicacionesWeb.repository.IdiomaRepository;
import com.example.ProyectoFinalAplicacionesWeb.repository.ProductoRepository;
import com.example.ProyectoFinalAplicacionesWeb.repository.VarianteProductoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VarianteProductoService {

    @Autowired
    private VarianteProductoRepository varianteProductoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private IdiomaRepository idiomaRepository;

    @Transactional
    public VarianteProducto guardarVariante(VarianteFormDTO form) {
        Producto producto = productoRepository.findById(form.getProductoId()).orElseThrow(() -> new IllegalArgumentException("El producto no existe"));

        List<Idioma> idiomas = idiomaRepository.findAllById(form.getIdiomaIds());
        if (idiomas.isEmpty()) {
            throw new IllegalArgumentException("Seleccione al menos un idioma.");
        }

        VarianteProducto variante = VarianteProducto.builder()
                .plataforma(form.getPlataforma())
                .precio(form.getPrecio())
                .stock(form.getStock())
                .activo(form.isActivo())
                .dimensiones(form.getDimensiones())
                .region(form.getRegion())
                .soporteFisico(form.getSoporteFisico())
                .producto(producto)
                .idiomas(idiomas)
                .build();

        return varianteProductoRepository.save(variante);
    }
}
