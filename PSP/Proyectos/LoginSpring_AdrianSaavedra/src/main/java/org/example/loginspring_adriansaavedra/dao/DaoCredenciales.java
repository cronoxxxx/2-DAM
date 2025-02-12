package org.example.loginspring_adriansaavedra.dao;

import org.example.loginspring_adriansaavedra.common.errors.UserAlreadyExistsException;
import org.example.loginspring_adriansaavedra.common.errors.UserNotFoundException;
import org.example.loginspring_adriansaavedra.domain.model.Credential;
import org.springframework.stereotype.Repository;

import java.util.Optional;


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
    public boolean findUserToRegister(String username) {
        Optional<Credential> credentialOptional = credentials.getCredentials()
                .stream()
                .filter(c -> c.getUsername().equals(username))
                .findFirst();
        if (credentialOptional.isPresent()) {
            throw new UserAlreadyExistsException("El usuario ya existe");
        }
        return true;
    }

    public Credential verifyUsername(String username) {
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