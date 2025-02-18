package org.example.loginspring_adriansaavedra.dao;

import org.example.loginspring_adriansaavedra.common.Constantes;
import org.example.loginspring_adriansaavedra.common.errors.UserAlreadyExistsException;
import org.example.loginspring_adriansaavedra.common.errors.UserNotFoundException;
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
                .filter(c -> c.getUsername().equals(credential.getUsername()) || c.getEmail().equals(credential.getEmail()))
                .findFirst()
                .orElse(null);
        if (existingCredential != null) {

            throw new UserAlreadyExistsException(Constantes.USER_ALREADY_EXISTS + credential.getUsername());
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
                .orElseThrow(() -> new UserNotFoundException(Constantes.INVALID_VERIFICATION_CODE));
    }

    public Credential authenticateUser(String username, String password) {
        Credential credential = credentials.getCredentials().stream()
                .filter(c -> c.getUsername().equals(username))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException(Constantes.USER_NOT_FOUND + username));
        if (!credential.isVerified() || !credential.getPassword().equals(password)) {
            throw new UserNotFoundException(Constantes.INVALID_CRED_OR_NOT_VERIFIED);
        }

        return credential;
    }
}