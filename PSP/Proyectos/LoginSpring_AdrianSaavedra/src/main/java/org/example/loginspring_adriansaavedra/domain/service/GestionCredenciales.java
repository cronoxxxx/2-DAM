package org.example.loginspring_adriansaavedra.domain.service;

import org.example.loginspring_adriansaavedra.dao.DaoCredenciales;
import org.example.loginspring_adriansaavedra.domain.components.MailComponent;
import org.example.loginspring_adriansaavedra.domain.model.Credential;
import org.example.loginspring_adriansaavedra.domain.validators.CredentialValidator;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class GestionCredenciales {
    private final DaoCredenciales daoCredenciales;
    private final MailComponent mailComponent;
    private final CredentialValidator credentialValidator;

    public GestionCredenciales(DaoCredenciales daoCredenciales, MailComponent mailComponent, CredentialValidator credentialValidator) {
        this.credentialValidator = credentialValidator;
        this.daoCredenciales = daoCredenciales;
        this.mailComponent = mailComponent;
    }

    public void registerUser(Credential credential) {
        if (credentialValidator.validateCredential(credential)) { //se puede lanzar una excepcion en el validator? pregunta oscar
            SecureRandom sr = new SecureRandom();
            byte[] verificationCodeBytes = new byte[16];
            sr.nextBytes(verificationCodeBytes);
            String verificationCode = Base64.getUrlEncoder().encodeToString(verificationCodeBytes);

            credential.setVerificationCode(verificationCode);
            credential.setVerified(false);

            daoCredenciales.verifyUserExists(credential);
            mailComponent.sendVerificationEmail(credential.getEmail(), verificationCode);
        }
    }

    public void verifyUser(String verificationCode) {
        Credential credential = daoCredenciales.findByVerificationCode(verificationCode);
        if (!credential.isVerified()) {
            credential.setVerified(true);
            credential.setVerificationCode(null);
            daoCredenciales.updateUserVerification(credential);
        }
    }

    public void authenticateUser(String username, String password) {
        daoCredenciales.authenticateUser(username, password);
    }
}
