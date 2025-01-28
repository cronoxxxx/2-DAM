package org.example.loginspring_adriansaavedra.domain.service;

import org.example.loginspring_adriansaavedra.dao.DaoCredenciales;
import org.example.loginspring_adriansaavedra.domain.model.Credential;
import org.example.loginspring_adriansaavedra.ui.components.MailComponent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class GestionCredenciales {
    private final DaoCredenciales daoCredenciales;
    private final MailComponent mailComponent;

    public GestionCredenciales(DaoCredenciales daoCredenciales, MailComponent mailComponent) {
        this.daoCredenciales = daoCredenciales;

        this.mailComponent = mailComponent;
    }

    public boolean registerUser(Credential credential) {
        if (daoCredenciales.findByUsername(credential.getUsername()) != null) {
            return false;
        }



        byte[] verificationCodeBytes = new byte[16];
        new SecureRandom().nextBytes(verificationCodeBytes);
        String verificationCode = Base64.getUrlEncoder().encodeToString(verificationCodeBytes);

        credential.setVerificationCode(verificationCode);
        credential.setVerified(false);

        daoCredenciales.save(credential);
        mailComponent.sendVerificationEmail(credential.getEmail(), verificationCode);

        return true;
    }

    public boolean verifyUser(String verificationCode) {
        Credential credential = daoCredenciales.findByVerificationCode(verificationCode);
        if (credential != null && !credential.isVerified()) {
            credential.setVerified(true);
            credential.setVerificationCode(null);
            daoCredenciales.save(credential);
            return true;
        }
        return false;
    }

    public boolean authenticateUser(String username, String password) {
        Credential credential = daoCredenciales.findByUsername(username);
        return credential != null && credential.isVerified();
    }
}
