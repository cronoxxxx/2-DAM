package org.example.loginspring_adriansaavedra.ui.model;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String email;
}