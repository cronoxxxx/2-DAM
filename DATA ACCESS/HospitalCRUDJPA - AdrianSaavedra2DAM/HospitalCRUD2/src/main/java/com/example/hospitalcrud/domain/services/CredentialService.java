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
        Credential credential = credentialRepository.get(userCredentials.getUsername());
        return credential != null && credential.getPassword() != null &&
                credential.getPassword().equals(userCredentials.getPassword());
    }

}
