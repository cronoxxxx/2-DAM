package org.example.loginspring_adriansaavedra.ui.filters;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.example.loginspring_adriansaavedra.common.Constantes;
import org.example.loginspring_adriansaavedra.ui.common.JwtTokenUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.apache.logging.log4j.util.Strings.isEmpty;

@Log4j2
@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;

    public JwtTokenFilter(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        log.debug("Processing request to {}", request.getRequestURI());
        if (request.getRequestURI().startsWith(Constantes.PLAYERS_DIR) || request.getRequestURI().startsWith(Constantes.FAVORITES_DIR)) {
            log.debug("Request requires authentication");
            final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (isEmpty(header) || !header.startsWith(Constantes.BEARER)) {
                log.warn("No valid authorization header found");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, Constantes.HEADER_PROVIDED_NEEDED);
                return;
            }

            final String token = header.split(" ")[1].trim();
            try {
                jwtTokenUtil.validateToken(token);
                String username = jwtTokenUtil.getUsernameFromToken(token);
                log.debug("Token validated for user: {}", username);
                request.setAttribute(Constantes.USERNAME, username);
            } catch (JwtException e) {
                log.warn("Invalid JWT token", e);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, Constantes.EXPIRED_CODE_MESSAGE);
                return;
            }
        }

        chain.doFilter(request, response);
    }
}