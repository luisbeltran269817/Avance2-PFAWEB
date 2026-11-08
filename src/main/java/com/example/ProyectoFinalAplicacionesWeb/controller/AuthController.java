package com.example.ProyectoFinalAplicacionesWeb.controller;

import com.example.ProyectoFinalAplicacionesWeb.dto.UsuarioRegistroDTO;
import com.example.ProyectoFinalAplicacionesWeb.model.Usuario;
import com.example.ProyectoFinalAplicacionesWeb.model.Administrador;
import com.example.ProyectoFinalAplicacionesWeb.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping({"/", "/inicioSesion"})
    public String mostrarLogin() {
        return "inicioSesion";
    }

    @PostMapping("/inicioSesion")
    public String procesarLogin(@RequestParam String correo,
                                @RequestParam String contrasenia,
                                HttpSession session,
                                Model model) {

        Usuario usuario = usuarioService.autenticarUsuario(correo, contrasenia);

        //modificado para validar si el id coincide con el administrador y mandarlo para el dashboard
        if (usuario != null) {
            boolean esAdmin = usuario instanceof Administrador;
            session.setAttribute("usuario_id", usuario.getId());
            session.setAttribute("es_admin", esAdmin);
            if (esAdmin) {
                return "redirect:/admin";
            }
            return "redirect:/catalogo";
        }

        model.addAttribute("error", "Credenciales inválidas. Inténtalo de nuevo.");
        return "inicioSesion";
    }

    @GetMapping("/registro")
    public String mostrarRegistro() {
        return "registro";
    }

    @PostMapping("/registro")
    public String procesarRegistro(@ModelAttribute UsuarioRegistroDTO form, Model model) {
        try {
            usuarioService.registrarUsuario(form);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "registro";
        }
        return "redirect:/inicioSesion?registrado=true";
    }

    @GetMapping("/cerrarSesion")
    public String cerrarSesion(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}