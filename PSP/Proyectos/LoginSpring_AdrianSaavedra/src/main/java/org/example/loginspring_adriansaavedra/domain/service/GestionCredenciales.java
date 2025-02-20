package org.example.loginspring_adriansaavedra.domain.service;

import org.example.loginspring_adriansaavedra.common.Constantes;
import org.example.loginspring_adriansaavedra.dao.DaoCredenciales;
import org.example.loginspring_adriansaavedra.domain.components.MailComponent;
import org.example.loginspring_adriansaavedra.domain.model.CredentialEntity;
import org.example.loginspring_adriansaavedra.domain.model.RoleEntity;
import org.example.loginspring_adriansaavedra.domain.validators.CredentialValidator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.Base64;

@Transactional
@Service
public class GestionCredenciales {
    private final DaoCredenciales daoCredenciales;
    private final MailComponent mailComponent;
    private final CredentialValidator credentialValidator;
    private final PasswordEncoder passwordEncoder;

    public GestionCredenciales(DaoCredenciales daoCredenciales, MailComponent mailComponent,
                               CredentialValidator credentialValidator, PasswordEncoder passwordEncoder) {
        this.credentialValidator = credentialValidator;
        this.daoCredenciales = daoCredenciales;
        this.mailComponent = mailComponent;
        this.passwordEncoder = passwordEncoder;
    }


    public void registerUser(CredentialEntity credentialEntity) {
        if (credentialValidator.validateCredential(credentialEntity)) {
            SecureRandom sr = new SecureRandom();
            byte[] verificationCodeBytes = new byte[16];
            sr.nextBytes(verificationCodeBytes);
            String verificationCode = Base64.getUrlEncoder().encodeToString(verificationCodeBytes);
            credentialEntity.setVerificationCode(verificationCode);
            credentialEntity.setVerified(false);
            credentialEntity.setPassword(passwordEncoder.encode(credentialEntity.getPassword()));

            RoleEntity userRole = daoCredenciales.findRoleByName(Constantes.USER_UPPER);
            credentialEntity.setRole(userRole);
            daoCredenciales.verifyUserExists(credentialEntity);

            mailComponent.sendVerificationEmail(credentialEntity.getEmail(), verificationCode);
        }
    }

    public void verifyToLoginUser(String verificationCode) {
        daoCredenciales.verifyAndUpdateUser(verificationCode);
    }


    public void authenticate(String username) {
        daoCredenciales.authenticateUser(username);
    }
}
