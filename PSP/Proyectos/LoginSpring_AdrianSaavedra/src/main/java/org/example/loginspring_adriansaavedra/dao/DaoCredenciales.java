package org.example.loginspring_adriansaavedra.dao;

import org.example.loginspring_adriansaavedra.domain.model.Credential;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DaoCredenciales {
    private final Database credentials;
    public DaoCredenciales (Database credentials) {
        this.credentials = credentials;
    }

    public void save(Credential credential) {
        credentials.getCredentials().removeIf(c -> c.getUsername().equals(credential.getUsername()));
        credentials.getCredentials().add(credential);
    }

    public Credential findByUsername(String username) {
        return credentials.getCredentials().stream()
                .filter(c -> c.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    public Credential findByVerificationCode(String verificationCode) {
        return credentials.getCredentials().stream()
                .filter(c -> verificationCode.equals(c.getVerificationCode()))
                .findFirst()
                .orElse(null);
    }
}