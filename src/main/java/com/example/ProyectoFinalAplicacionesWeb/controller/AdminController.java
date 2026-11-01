package com.example.ProyectoFinalAplicacionesWeb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.example.ProyectoFinalAplicacionesWeb.service.InventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

@Controller
public class AdminController {

    @Autowired
    private InventarioService inventarioService;

    @GetMapping("/admin")
    public String modoAdministrador(Model model) {
        model.addAttribute("variantes", inventarioService.listarVariantes());
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
    public String seleccionarVarianteProducto(){
        return "seleccionarVarianteProducto";
    }

    @GetMapping("/admin/variantes/nuevo")
    public String crearVarianteProducto(){
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

    @GetMapping("/admin/stock")
    public String ajusteStock() {
        return "ajusteStock";
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
