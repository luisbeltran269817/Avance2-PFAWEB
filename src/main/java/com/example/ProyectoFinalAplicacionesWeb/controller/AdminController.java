package com.example.ProyectoFinalAplicacionesWeb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdminController {

    @GetMapping("/admin")
    public String modoAdministrador() {
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
}