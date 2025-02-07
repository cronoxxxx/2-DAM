package com.example.hospitalcrud.domain.model;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class CredentialUI {
    private String username;
    private String password;

    public CredentialUI(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
