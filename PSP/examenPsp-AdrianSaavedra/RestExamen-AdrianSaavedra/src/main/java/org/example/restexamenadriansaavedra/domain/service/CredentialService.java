package org.example.restexamenadriansaavedra.domain.service;

import org.example.restexamenadriansaavedra.dao.DaoCredential;
import org.example.restexamenadriansaavedra.domain.model.Credential;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CredentialService {
    private final DaoCredential daoCredential;
    private final PasswordEncoder passwordEncoder;

    public CredentialService(DaoCredential daoCredential, PasswordEncoder passwordEncoder) {
        this.daoCredential = daoCredential;
        this.passwordEncoder = passwordEncoder;
    }
    public void registerUser(Credential user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        daoCredential.save(user);
    }
}
