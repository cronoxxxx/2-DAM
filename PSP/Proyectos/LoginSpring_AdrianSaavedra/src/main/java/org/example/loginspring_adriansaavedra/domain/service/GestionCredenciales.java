package org.example.loginspring_adriansaavedra.domain.service;

import org.example.loginspring_adriansaavedra.dao.DaoCredenciales;
import org.example.loginspring_adriansaavedra.domain.components.MailComponent;
import org.example.loginspring_adriansaavedra.domain.model.CredentialEntity;
import org.example.loginspring_adriansaavedra.domain.model.RoleEntity;
import org.example.loginspring_adriansaavedra.domain.validators.CredentialValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public void registerUser(CredentialEntity credentialEntity) {
        if (credentialValidator.validateCredential(credentialEntity)) {
            SecureRandom sr = new SecureRandom();
            byte[] verificationCodeBytes = new byte[16];
            sr.nextBytes(verificationCodeBytes);
            String verificationCode = Base64.getUrlEncoder().encodeToString(verificationCodeBytes);

            credentialEntity.setVerificationCode(verificationCode);
            credentialEntity.setVerified(false);

            RoleEntity userRole = daoCredenciales.findRoleByName("USER");
            credentialEntity.setRole(userRole);

            daoCredenciales.verifyUserExists(credentialEntity);
            mailComponent.sendVerificationEmail(credentialEntity.getEmail(), verificationCode);
        }
    }
    @Transactional
    public void verifyToLoginUser(String verificationCode) {
        CredentialEntity credentialEntity = daoCredenciales.findByVerificationCode(verificationCode);
        if (!credentialEntity.isVerified()) {
            credentialEntity.setVerified(true);
            credentialEntity.setVerificationCode(null);
            daoCredenciales.updateUserVerification(credentialEntity);
        }
    }

    public CredentialEntity authenticateUser(String username, String password) {
        return daoCredenciales.authenticateUser(username, password);
    }
}
