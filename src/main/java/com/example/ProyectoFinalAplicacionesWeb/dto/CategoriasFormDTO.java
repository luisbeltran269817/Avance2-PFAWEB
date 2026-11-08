package com.example.ProyectoFinalAplicacionesWeb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

//este es para el formulario de editar categorias
//si ocupan un dto de categoria para otra cosa, vayan a CategoriaDTO
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriasFormDTO {

    //las categorias que ya existen, cada una con su tarjeta
    private List<CategoriaDTO> categorias = new ArrayList<>();

    //datos de la tarjeta "añadir nueva categoria", se ignora si el nombre viene vacio
    private CategoriaDTO nueva = new CategoriaDTO();
}
