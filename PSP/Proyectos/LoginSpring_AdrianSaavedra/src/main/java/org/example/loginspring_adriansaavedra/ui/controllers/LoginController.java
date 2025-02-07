package org.example.loginspring_adriansaavedra.ui.controllers;

import org.example.loginspring_adriansaavedra.domain.model.Credential;
import org.example.loginspring_adriansaavedra.domain.service.GestionCredenciales;
import org.example.loginspring_adriansaavedra.ui.common.JwtTokenUtil;
import org.example.loginspring_adriansaavedra.ui.model.LoginRequest;
import org.example.loginspring_adriansaavedra.ui.model.RegisterRequest;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        if (gestionCredenciales.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword())) {
            String token = jwtTokenUtil.generateToken(loginRequest.getPassword()); //pregunta oscar
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password, or account not verified");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        Credential credential = Credential.builder()
                .username(registerRequest.getUsername())
                .password(registerRequest.getPassword())
                .email(registerRequest.getEmail())
                .build();

        if (gestionCredenciales.registerUser(credential)) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Registration successful. Please check your email to verify your account.");
        } else {
            return ResponseEntity.badRequest().body("Username already exists");
        }
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyUser(@RequestParam String code) {
        if (gestionCredenciales.verifyUser(code)) {
            return ResponseEntity.ok("Your account has been verified. You can now log in.");
        } else {
            return ResponseEntity.badRequest().body("Invalid or expired verification code.");
        }
    }
}