package org.example.loginspring_adriansaavedra.ui.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.example.loginspring_adriansaavedra.common.Constantes;
import org.example.loginspring_adriansaavedra.domain.model.Credential;
import org.example.loginspring_adriansaavedra.domain.model.Login;
import org.example.loginspring_adriansaavedra.domain.model.Register;
import org.example.loginspring_adriansaavedra.domain.service.GestionCredenciales;
import org.example.loginspring_adriansaavedra.ui.common.JwtTokenUtil;
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
    public ResponseEntity<String> login(@RequestBody Login login) {
        if (gestionCredenciales.authenticateUser(login.getUsername(), login.getPassword())) {
            String token = jwtTokenUtil.generateToken(login.getUsername());
            return ResponseEntity.status(HttpServletResponse.SC_OK).body(token);
        } else {
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED)
                    .body(Constantes.ERROR_MESSAGE);
        }
    }

    @PostMapping(Constantes.REGISTER_DIR)
    public ResponseEntity<String> register(@RequestBody Register register) {
        Credential credential = Credential.builder()
                .username(register.getUsername())
                .password(register.getPassword())
                .email(register.getEmail())
                .build();

        gestionCredenciales.registerUser(credential);
            return ResponseEntity.status(HttpServletResponse.SC_CREATED)
                    .body(Constantes.SUCCESS_REGISTER_MESSAGE);

    }

    @GetMapping(Constantes.VERIFY_DIR)
    public ResponseEntity<String> verifyUser(@RequestParam String code) {
        if (gestionCredenciales.verifyUser(code)) {
            return ResponseEntity.status(HttpServletResponse.SC_OK)
                    .body(Constantes.SUCCESS_MESSAGE);
        } else {
            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST)
                    .body(Constantes.EXPIRED_CODE_MESSAGE);
        }
    }
}