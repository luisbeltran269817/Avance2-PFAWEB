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

    @GetMapping({"/"})
    public String inicioSesion(){
        return "inicioSesion";
    }
}
