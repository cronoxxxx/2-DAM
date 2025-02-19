package org.example.loginspring_adriansaavedra.ui.controllers;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletResponse;
import org.example.loginspring_adriansaavedra.common.Constantes;
import org.example.loginspring_adriansaavedra.domain.model.CredentialEntity;
import org.example.loginspring_adriansaavedra.domain.model.Login;
import org.example.loginspring_adriansaavedra.domain.model.Register;
import org.example.loginspring_adriansaavedra.domain.service.GestionCredenciales;
import org.example.loginspring_adriansaavedra.ui.common.JwtTokenUtil;
import org.example.loginspring_adriansaavedra.ui.utils.AuthenticationResponse;
import org.example.loginspring_adriansaavedra.ui.utils.RefreshTokenRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

    private final GestionCredenciales gestionCredenciales;
    private final JwtTokenUtil jwtTokenUtil;

    public LoginController(GestionCredenciales gestionCredenciales, JwtTokenUtil jwtTokenUtil) {
        this.gestionCredenciales = gestionCredenciales;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping(Constantes.LOGIN_DIR)
    public ResponseEntity<AuthenticationResponse> login(@RequestBody Login login) {
       CredentialEntity c = gestionCredenciales.authenticateUser(login.getUsername(), login.getPassword());
        String accessToken = jwtTokenUtil.generateAccessToken(c.getUsername());
        String refreshToken = jwtTokenUtil.generateRefreshToken(c.getUsername());
        AuthenticationResponse response = AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        return ResponseEntity.status(HttpServletResponse.SC_OK).body(response);
    }

    @PostMapping(Constantes.REGISTER_DIR)
    public ResponseEntity<String> register(@RequestBody Register register) {
        CredentialEntity credentialEntity = CredentialEntity.builder()
                .username(register.getUsername())
                .password(register.getPassword())
                .email(register.getEmail())
                .build();

        gestionCredenciales.registerUser(credentialEntity);
        return ResponseEntity.status(HttpServletResponse.SC_CREATED)
                .body(Constantes.SUCCESS_REGISTER);

    }

    @GetMapping(Constantes.VERIFY_DIR)
    public ResponseEntity<String> verifyUser(@RequestParam String code) {
        gestionCredenciales.verifyToLoginUser(code);
        return ResponseEntity.status(HttpServletResponse.SC_OK)
                .body(Constantes.SUCCESS_MESSAGE);

    }

    @PostMapping(Constantes.REFRESH_DIR)
    public ResponseEntity<AuthenticationResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
        try {
            String refreshToken = request.getRefreshToken();
            jwtTokenUtil.validateToken(refreshToken);
            String username = jwtTokenUtil.getUsernameFromToken(refreshToken);
            String newAccessToken = jwtTokenUtil.generateAccessToken(username);
            AuthenticationResponse response = AuthenticationResponse.builder()
                    .accessToken(newAccessToken)
                    .refreshToken(refreshToken)
                    .build();
            return ResponseEntity.status(HttpServletResponse.SC_OK).body(response);
        } catch (JwtException e) {
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).build();
        }
    }
}