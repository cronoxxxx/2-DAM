package org.example.loginspring_adriansaavedra.ui.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.example.loginspring_adriansaavedra.common.Constantes;
import org.example.loginspring_adriansaavedra.domain.model.Credential;
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
        Credential credential = gestionCredenciales.authenticateUser(login.getUsername(), login.getPassword());
        String accessToken = jwtTokenUtil.generateAccessToken(credential.getId());
        String refreshToken = jwtTokenUtil.generateRefreshToken(credential.getId());
        AuthenticationResponse response = AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        return ResponseEntity.status(HttpServletResponse.SC_OK).body(response);
    }

    @PostMapping(Constantes.REGISTER_DIR)
    public ResponseEntity<Void> register(@RequestBody Register register) {
        Credential credential = Credential.builder()
                .username(register.getUsername())
                .password(register.getPassword())
                .email(register.getEmail())
                .build();

        gestionCredenciales.registerUser(credential);
        return ResponseEntity.status(HttpServletResponse.SC_CREATED)
                .build();

    }

    @GetMapping(Constantes.VERIFY_DIR)
    public ResponseEntity<String> verifyUser(@RequestParam String code) {
        gestionCredenciales.verifyToLoginUser(code);
        return ResponseEntity.status(HttpServletResponse.SC_OK)
                .body(Constantes.SUCCESS_MESSAGE);

    }

    @PostMapping(Constantes.REFRESH_DIR)
    public ResponseEntity<AuthenticationResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
        String refreshToken = request.getRefreshToken();
        jwtTokenUtil.validateToken(refreshToken);
        int userId = jwtTokenUtil.getUserIdFromToken(refreshToken);
        String newAccessToken = jwtTokenUtil.generateAccessToken(userId);
        AuthenticationResponse response = AuthenticationResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(refreshToken)
                .build();
        return ResponseEntity.status(HttpServletResponse.SC_OK).body(response);

    }
}