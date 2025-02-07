package org.example.loginspring_adriansaavedra.ui.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.example.loginspring_adriansaavedra.ui.common.JwtTokenUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.apache.logging.log4j.util.Strings.isEmpty;


@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;

    public JwtTokenFilter(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        // Only apply the filter to /players endpoints
        if (request.getRequestURI().startsWith("/players")) {
            final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (isEmpty(header) || !header.startsWith("Bearer ")) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authorization header must be provided");
                return;
            }

            final String token = header.split(" ")[1].trim();
            if (!jwtTokenUtil.validateToken(token)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
                return;
            }
        }

        chain.doFilter(request, response);
    }
}
