package com.example.ProyectoFinalAplicacionesWeb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GameController {

    @GetMapping({"/prueba"})
    public String prueba(){
        return "prueba";
    }

    @GetMapping({"/","inicioSesion"})
    public String inicioSesion(){
        return "inicioSesion";
    }

    @GetMapping({"/registro"})
    public String registro(){
        return "registro";
    }

    @GetMapping({"/perfil"})
    public String perfil() {
        return "perfil";
    }

    @GetMapping({"/carrito"})
    public String carrito(){
        return "carrito";
    }
}
