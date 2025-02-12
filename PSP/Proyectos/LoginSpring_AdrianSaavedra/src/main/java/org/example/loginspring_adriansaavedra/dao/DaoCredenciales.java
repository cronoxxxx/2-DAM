package org.example.loginspring_adriansaavedra.dao;

import org.example.loginspring_adriansaavedra.common.errors.UserNotFoundException;
import org.example.loginspring_adriansaavedra.domain.model.Credential;
import org.springframework.stereotype.Repository;


@Repository
public class DaoCredenciales {
    private final Database credentials;

    public DaoCredenciales(Database credentials) {
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
                .orElseThrow(() -> new UserNotFoundException("Credencial no encontrada"));
    }

    public Credential findByVerificationCode(String verificationCode) {
        return credentials.getCredentials().stream()
                .filter(c -> verificationCode.equals(c.getVerificationCode()))
                .findFirst()
                .orElse(null);
    }
}