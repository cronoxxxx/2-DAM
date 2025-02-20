package org.example.loginspring_adriansaavedra.ui.controllers;

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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
public class SessionController {

    private final GestionCredenciales gestionCredenciales;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    public SessionController(GestionCredenciales gestionCredenciales, JwtTokenUtil jwtTokenUtil, AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
        this.gestionCredenciales = gestionCredenciales;
        this.jwtTokenUtil = jwtTokenUtil;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping(Constantes.LOGIN_DIR)
    public ResponseEntity<AuthenticationResponse> login(@RequestBody Login login) {
        gestionCredenciales.authenticate(login.getUsername());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String accessToken = jwtTokenUtil.generateAccessToken(userDetails);
        String refreshToken = jwtTokenUtil.generateRefreshToken(userDetails);

        AuthenticationResponse response = AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        return ResponseEntity.status(HttpServletResponse.SC_OK)
                .body(response);
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
        String refreshToken = request.getRefreshToken();
        jwtTokenUtil.validateToken(refreshToken);
        String username = jwtTokenUtil.getUsernameFromToken(refreshToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        String newAccessToken = jwtTokenUtil.generateAccessToken(userDetails);
        AuthenticationResponse response = AuthenticationResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(refreshToken)
                .build();
        return ResponseEntity.status(HttpServletResponse.SC_OK).body(response);
    }


}