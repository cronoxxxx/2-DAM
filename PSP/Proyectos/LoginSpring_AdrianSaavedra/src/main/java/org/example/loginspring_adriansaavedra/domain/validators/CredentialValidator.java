package org.example.loginspring_adriansaavedra.domain.validators;

import org.example.loginspring_adriansaavedra.common.Constantes;
import org.example.loginspring_adriansaavedra.common.errors.UserValidatorException;
import org.example.loginspring_adriansaavedra.domain.model.CredentialEntity;
import org.springframework.stereotype.Component;

@Component
public class CredentialValidator {
    public boolean validateCredential(CredentialEntity credentialEntity) {
        if (credentialEntity.getUsername().isBlank() || credentialEntity.getPassword().isBlank() || credentialEntity.getEmail().isBlank()) {
            throw new UserValidatorException(Constantes.COMPULSORY_FILLED);
        } else {
            return true;
        }
    }
}
