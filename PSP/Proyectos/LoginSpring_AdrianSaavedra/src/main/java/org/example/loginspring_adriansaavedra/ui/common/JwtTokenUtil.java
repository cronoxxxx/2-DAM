package org.example.loginspring_adriansaavedra.ui.common;

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

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuer(Constantes.SERVIDOR)
                .setIssuedAt(new Date())
                .setExpiration(Date.from(LocalDateTime.now().plusSeconds(60).atZone(ZoneId.systemDefault()).toInstant()))
                .claim(Constantes.USER_S, username)
                .signWith(key)
                .compact();
    }

    public void validateToken(String token) {

            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);


    }


}