package com.example.ProyectoFinalAplicacionesWeb.service;

import com.example.ProyectoFinalAplicacionesWeb.dto.CategoriaDTO;
import com.example.ProyectoFinalAplicacionesWeb.dto.IdiomaDTO;
import com.example.ProyectoFinalAplicacionesWeb.model.Categoria;
import com.example.ProyectoFinalAplicacionesWeb.model.Idioma;
import com.example.ProyectoFinalAplicacionesWeb.repository.IdiomaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IdiomaService {

    @Autowired
    IdiomaRepository idiomaRepository;

    /**
     * metodo para listar todos los idiomas de la DB como DTO
     * @return
     */
    public List<IdiomaDTO> listarIdiomas(){
        return idiomaRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    //auxiliar par convertir a dto
    private IdiomaDTO convertirADTO(Idioma idioma) {
        IdiomaDTO dto = new IdiomaDTO();
        dto.setId(idioma.getId());
        dto.setNombre(idioma.getNombre());
        return dto;
    }
}
