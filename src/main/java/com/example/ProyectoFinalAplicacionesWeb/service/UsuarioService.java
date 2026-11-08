package com.example.ProyectoFinalAplicacionesWeb.service;

import com.example.ProyectoFinalAplicacionesWeb.dto.UsuarioDTO;
import com.example.ProyectoFinalAplicacionesWeb.dto.UsuarioRegistroDTO;
import com.example.ProyectoFinalAplicacionesWeb.model.Usuario;
import com.example.ProyectoFinalAplicacionesWeb.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

//registro, login y lectura del usuario en sesion
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private HttpSession session;

    public Usuario registrarUsuario(UsuarioRegistroDTO form) {
        String correo = form.getCorreo().trim().toLowerCase();

        if (usuarioRepository.existsByCorreo(correo)) {
            throw new IllegalArgumentException("El correo ya esta registrado");
        }

        Usuario usuario = Usuario.builder()
                .nombre(form.getNombre().trim())
                .correo(correo)
                .contrasena(passwordEncoder.encode(form.getContrasenia()))
                .telefono(form.getTelefono())
                .fechaRegistro(LocalDateTime.now())
                .build();

        return usuarioRepository.save(usuario);
    }

    public Usuario autenticarUsuario(String correo, String contrasena) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByCorreo(correo.trim().toLowerCase());

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            if (passwordEncoder.matches(contrasena, usuario.getContrasena())) {
                return usuario;
            }
        }
        return null;
    }

    public UsuarioDTO obtenerUsuarioLogueado() {
        Long usuarioId = (Long) session.getAttribute("usuario_id");

        if (usuarioId != null) {
            return usuarioRepository.findById(usuarioId)
                    .map(this::convertirADTO)
                    .orElse(null);
        }
        return null;
    }

    //metodo auxiliar que convierte un usuario en usuariodto, sin la contrasenia
    private UsuarioDTO convertirADTO(Usuario usuario) {
        return new UsuarioDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getCorreo(),
                usuario.getTelefono(),
                usuario.getFechaRegistro()
        );
    }
}

