package com.example.ProyectoFinalAplicacionesWeb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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

    @GetMapping("/admin/stock")
    public String ajusteStock() {
        return "ajusteStock";
    }
}
