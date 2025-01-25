package org.example.loginspring_adriansaavedra.ui.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.example.loginspring_adriansaavedra.common.Constantes;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Log4j2
@Component
public class SessionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        log.info("SessionFilter processing request for path: {}", path);

        HttpSession session = request.getSession(false);
        boolean isLoggedIn = (session != null && session.getAttribute(Constantes.USER_ATTRIBUTE) != null);

        if (!isLoggedIn && !"/login".equals(path) && !"/logout".equals(path)) {
            log.info("Unauthorized access attempt. Redirecting to login page.");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        if (isLoggedIn && "/login".equals(path)) {
            log.info("Already logged in. Redirecting to players page.");
            response.sendRedirect(request.getContextPath() + "/players");
            return;
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return "/logout".equals(path); // We don't need to filter logout requests
    }


}