package com.example.ProyectoFinalAplicacionesWeb.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // false: no crea una sesión nueva, solo revisa si ya existe.
        HttpSession session = request.getSession(false);

        // Sin sesión o sin usuario_id = no está logueado; lo mandamos al login.
        if (session == null || session.getAttribute("usuario_id") == null) {
            response.sendRedirect("/inicioSesion");
            return false;
        }

        // Rutas /admin
        if (request.getRequestURI().startsWith("/admin")
                && !Boolean.TRUE.equals(session.getAttribute("es_admin"))) {
            response.sendRedirect("/catalogo");
            return false;
        }

        return true;
    }
}
