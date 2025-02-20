package org.example.loginspring_adriansaavedra.ui.common;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.example.loginspring_adriansaavedra.common.Constantes;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.security.core.GrantedAuthority;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JwtTokenUtil {

    private final Key key;

    public JwtTokenUtil(Key key) {
        this.key = key;
    }

    public String generateAccessToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuer(Constantes.SERVIDOR)
                .setIssuedAt(new Date())
                .setExpiration(Date.from(LocalDateTime.now().plusMinutes(5).atZone(ZoneId.systemDefault()).toInstant()))
                .claim(Constantes.USER_S, userDetails.getUsername())
                .claim(Constantes.ROLES, userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .signWith(key)
                .compact();
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuer(Constantes.SERVIDOR)
                .setIssuedAt(new Date())
                .setExpiration(Date.from(LocalDateTime.now().plusDays(1).atZone(ZoneId.systemDefault()).toInstant()))
                .claim(Constantes.USER_S, userDetails.getUsername())
                .claim(Constantes.ROLES, userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .signWith(key)
                .compact();
    }

    public void validateToken(String token) {
        Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
    }

    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
}