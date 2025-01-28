package org.example.loginspring_adriansaavedra.dao;

import org.example.loginspring_adriansaavedra.domain.model.Credential;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DaoCredenciales {
    private final List<Credential> credentials = new ArrayList<>();

    public void save(Credential credential) {
        credentials.removeIf(c -> c.getUsername().equals(credential.getUsername()));
        credentials.add(credential);
    }

    public Credential findByUsername(String username) {
        return credentials.stream()
                .filter(c -> c.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    public Credential findByVerificationCode(String verificationCode) {
        return credentials.stream()
                .filter(c -> verificationCode.equals(c.getVerificationCode()))
                .findFirst()
                .orElse(null);
    }
}