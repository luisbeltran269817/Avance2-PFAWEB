package com.example.ProyectoFinalAplicacionesWeb.controller;

import com.example.ProyectoFinalAplicacionesWeb.dto.ProductoDTO;
import com.example.ProyectoFinalAplicacionesWeb.service.ProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.example.ProyectoFinalAplicacionesWeb.service.InventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import com.example.ProyectoFinalAplicacionesWeb.dto.VarianteInventarioDTO;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private InventarioService inventarioService;

    @Autowired
    private ProductoService productoService;

    @GetMapping("/admin")
    public String modoAdministrador(Model model) {
        List<VarianteInventarioDTO> inventario = inventarioService.listarInventario();
        model.addAttribute("inventario", inventario);
        model.addAttribute("resumen", inventarioService.calcularResumen(inventario));
        return "modoAdministrador";
    }

    @GetMapping("/admin/nuevo-titulo")
    public String nuevoTitulo() {
        return "seleccionarNuevoTitulo";
    }

    @GetMapping("/admin/productos/nuevo")
    public String nuevoProducto() {
        return "añadirProducto";
    }

    @PostMapping("/admin/productos/guardarProducto")
    public String guardarProducto() {
        //falta ponerle la logica de guardado
        //solo redirige al admin dashboard por ahora
        return "redirect:/admin";
    }

    @GetMapping("/admin/variantes/seleccionar")
    public String seleccionarVarianteProducto(@RequestParam(required = false) String buscar, Model model) {
        List<ProductoDTO> productos = productoService.buscarProductos(buscar);
        model.addAttribute("productos", productos);
        model.addAttribute("buscar", buscar);
        return "seleccionarVarianteProducto";
    }

    @GetMapping("/admin/variantes/nuevo")
    //el id del producto lo recibe de la pantalla anterior, aun no pongo logica
    public String crearVarianteProducto(@RequestParam Long productoId, Model model) {
        return "añadirVarianteProducto";
    }

    @PostMapping("/admin/variantes/guardarVariante")
    public String guardarVariante() {
        //falta ponerle la logica de guardado
        //solo redirige al admin dashboard por ahora
        return "redirect:/admin";
    }

    @GetMapping("/admin/productos/editar")
    public String editarProducto() {
        return "editarProducto";
    }
    
    @GetMapping("/admin/stock/{id}")
    public String ajusteStock(@PathVariable Long id, Model model) {
        VarianteInventarioDTO variante = inventarioService.obtenerVariante(id);
        if (variante == null) {
            return "redirect:/admin";
        }
        model.addAttribute("variante", variante);
        return "ajusteStock";
    }

    @PostMapping("/admin/stock/{id}")
    public String guardarAjuste(@PathVariable Long id,
                                @RequestParam("cantidad") int cantidad,
                                @RequestParam("motivo") String motivo) {
        boolean registrado = inventarioService.registrarAjuste(id, cantidad, motivo);
        if (!registrado) {
            return "redirect:/admin/stock/" + id;
        }
        return "redirect:/admin";
    }

     @GetMapping("/admin/editarCategoria")
    public String editarCategorias() {
        return "editarCategoria";
    }

    @PostMapping("/admin/editarCategoria")
    public String guardarCategorias() {
        //falta ponerle la logica de guardado
        //solo redirige al admin dashboard por ahora
        return "redirect:/admin";
    }

    @PostMapping("/admin/editarCategoria/{id}/eliminar")
    public String eliminarCategoria(@PathVariable Long id) {
        //falta ponerle la logica de eliminado
        return "redirect:/admin/categorias";
    }
}
