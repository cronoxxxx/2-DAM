package org.example.loginspring_adriansaavedra.ui.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.loginspring_adriansaavedra.common.Constantes;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class SessionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute(Constantes.USER) == null) {
            response.sendRedirect(request.getContextPath() + Constantes.LOGIN_DIR);
            return;
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request){
        String path = request.getRequestURI().substring(request.getContextPath().length());
        return !(path.startsWith(Constantes.PLAYERS_DIR) || path.startsWith(Constantes.UPDATE_DIR));
    }
}