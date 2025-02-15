package org.example.loginspring_adriansaavedra.dao;

import org.example.loginspring_adriansaavedra.common.errors.*;
import org.example.loginspring_adriansaavedra.domain.model.Credential;
import org.springframework.stereotype.Repository;


@Repository
public class DaoCredenciales {
    private final Database credentials;

    public DaoCredenciales(Database credentials) {
        this.credentials = credentials;
    }

    public void verifyUserExists(Credential credential) {
        Credential existingCredential = credentials.getCredentials().stream()
                .filter(c -> c.getUsername().equals(credential.getUsername()))
                .findFirst()
                .orElse(null);
        if (existingCredential != null) {

            throw new UserAlreadyExistsException("El usuario " + credential.getUsername() + " ya existe");
        } else {
            int maxId = credentials.getCredentials().stream()
                    .mapToInt(Credential::getId)
                    .max()
                    .orElse(0) + 1;
            credential.setId(maxId);
            credentials.getCredentials().add(credential);
        }


    }

    public void updateUserVerification(Credential credential) {
        Credential existingCredential = credentials.getCredentials().stream()
                .filter(c -> c.getUsername().equals(credential.getUsername()))
                .findFirst()
                .orElse(null);
        if (existingCredential != null) {
            existingCredential.setVerified(credential.isVerified());
            existingCredential.setVerificationCode(credential.getVerificationCode());
        }
    }

    public Credential findByVerificationCode(String verificationCode) {
        return credentials.getCredentials().stream()
                .filter(c -> verificationCode.equals(c.getVerificationCode()))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("Código de verificación inválido"));
    }
    public void authenticateUser(String username, String password) {
        Credential credential = credentials.getCredentials().stream()
                .filter(c -> c.getUsername().equals(username))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado: " + username));
        if (!credential.isVerified() || !credential.getPassword().equals(password)) {
            throw new UserNotFoundException("Credenciales inválidas o usuario no verificado");
        }
    }
}