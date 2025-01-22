package com.example.hospitalcrud.domain.services;

import com.example.hospitalcrud.dao.model.Credential;
import com.example.hospitalcrud.dao.repositories.CredentialRepository;
import com.example.hospitalcrud.domain.model.CredentialUI;
import org.springframework.stereotype.Service;

@Service
public class CredentialService {
    private final CredentialRepository credentialRepository;

    public CredentialService(CredentialRepository credentialRepository) {
        this.credentialRepository = credentialRepository;
    }

    public boolean login(CredentialUI userCredentials) {
        return credentialRepository.findByUserName(userCredentials.getUsername())
                .map(credential -> credential.getPassword().equals(userCredentials.getPassword()))
                .orElse(false);
    }
}
