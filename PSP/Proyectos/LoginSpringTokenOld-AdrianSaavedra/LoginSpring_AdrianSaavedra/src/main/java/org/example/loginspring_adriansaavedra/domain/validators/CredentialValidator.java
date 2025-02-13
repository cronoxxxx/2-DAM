package org.example.loginspring_adriansaavedra.domain.validators;

import org.example.loginspring_adriansaavedra.domain.model.Credential;
import org.springframework.stereotype.Component;

@Component
public class CredentialValidator {
    public boolean validateCredential(Credential credential) {
        return credential.getUsername().isBlank() || credential.getPassword().isBlank() || credential.getEmail().isBlank();
    }
}
