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

    @GetMapping({"/carrito"})
    public String carrito(){
        return "carrito";
    }

    @GetMapping({"/finalizarCompra"})
    public String finalizarCompra(){
        return "finalizarCompra";
    }

    @GetMapping({"/agregarDireccion"})
    public String agregarDireccion(){
        return "agregarDireccion";
    }

    @GetMapping({"/pagoConfirmado"})
    public String pagoConfirmado(){
        return "pagoConfirmado";
    }

    @GetMapping({"/catalogo"})
    public String catalogo(){
        return "catalogo";
    }
}
