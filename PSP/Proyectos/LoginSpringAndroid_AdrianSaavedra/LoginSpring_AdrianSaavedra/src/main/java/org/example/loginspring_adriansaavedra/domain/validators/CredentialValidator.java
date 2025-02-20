package org.example.loginspring_adriansaavedra.domain.validators;

import org.example.loginspring_adriansaavedra.common.Constantes;
import org.example.loginspring_adriansaavedra.common.errors.UserValidatorException;
import org.example.loginspring_adriansaavedra.domain.model.Credential;
import org.springframework.stereotype.Component;

@Component
public class CredentialValidator {
    public boolean validateCredential(Credential credential) {
        if (credential.getUsername().isBlank() || credential.getPassword().isBlank() || credential.getEmail().isBlank()) {
            throw new UserValidatorException(Constantes.COMPULSORY_FILLED);
        } else {
            return true;
        }
    }
}
