package org.example.restexamenadriansaavedra.ui.controllers;

import org.example.restexamenadriansaavedra.domain.model.Credential;
import org.example.restexamenadriansaavedra.domain.service.CredentialService;
import org.example.restexamenadriansaavedra.ui.common.AuthenticationResponse;
import org.example.restexamenadriansaavedra.ui.common.JwtTokenUtil;
import org.example.restexamenadriansaavedra.ui.common.Login;
import org.example.restexamenadriansaavedra.ui.common.RefreshTokenRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {


    private final CredentialService credentialService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    public LoginController(CredentialService credentialService, JwtTokenUtil jwtTokenUtil1, AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
        this.credentialService = credentialService;
        this.jwtTokenUtil = jwtTokenUtil1;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody Login loginUser) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String accessToken = jwtTokenUtil.generateAccessToken(userDetails);
        String refreshToken = jwtTokenUtil.generateRefreshToken(userDetails);

        AuthenticationResponse response = AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<String > registerUser(@RequestBody Credential user) {
        credentialService.registerUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");

    }

    @PostMapping("/refresh")
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
        return ResponseEntity.ok(response);

    }
}
