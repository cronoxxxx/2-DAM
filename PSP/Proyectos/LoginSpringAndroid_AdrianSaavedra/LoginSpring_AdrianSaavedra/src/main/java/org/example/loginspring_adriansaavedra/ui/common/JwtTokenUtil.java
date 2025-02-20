package org.example.loginspring_adriansaavedra.ui.common;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.example.loginspring_adriansaavedra.common.Constantes;
import org.springframework.stereotype.Component;

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

    public String generateAccessToken(int userId) {
        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .setIssuer(Constantes.SERVIDOR)
                .setIssuedAt(new Date())
                .setExpiration(Date.from(LocalDateTime.now().plusSeconds(10).atZone(ZoneId.systemDefault()).toInstant()))
                .claim(Constantes.USER_ID_S, userId)
                .signWith(key)
                .compact();
    }

    public String generateRefreshToken(int userId) {
        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .setIssuer(Constantes.SERVIDOR)
                .setIssuedAt(new Date())
                .setExpiration(Date.from(LocalDateTime.now().plusMinutes(15).atZone(ZoneId.systemDefault()).toInstant()))
                .claim(Constantes.USER_ID_S, userId)
                .signWith(key)
                .compact();
    }

    public void validateToken(String token) {
        Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
    }

    public int getUserIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return Integer.parseInt(claims.getSubject());
    }
}