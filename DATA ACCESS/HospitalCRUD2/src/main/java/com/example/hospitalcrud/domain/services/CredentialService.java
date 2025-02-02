package com.example.hospitalcrud.domain.services;

import com.example.hospitalcrud.common.FetchType;
import com.example.hospitalcrud.common.JsonConverter;
import com.example.hospitalcrud.common.UserType;
import com.example.hospitalcrud.dao.model.Credential;
import com.example.hospitalcrud.dao.repositories.CredentialRepository;
import com.example.hospitalcrud.domain.model.CredentialUI;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {
    private final CredentialRepository credentialRepository;
    private final JsonConverter jsonConverter;

    public CredentialService(CredentialRepository credentialRepository, JsonConverter jsonConverter) {
        this.credentialRepository = credentialRepository;
        this.jsonConverter = jsonConverter;
    }

    public boolean login(CredentialUI userCredentials) {
        return credentialRepository.findByUserName(userCredentials.getUsername())
                .map(credential -> {
                    if (credential.getPassword().equals(userCredentials.getPassword())) {
                        updateFetchTypes(credential);
                        return true;
                    }
                    return false;
                })
                .orElse(false);
    }

    private void updateFetchTypes(Credential credential) {
        List<FetchType> fetchTypes = jsonConverter.loadFetchTypes();
        UserType userType = credential.getUserType();
        int userId = switch (userType) {
            case PATIENT -> credential.getPatient().getId();
            case DOCTOR -> credential.getDoctorId();
            case ADMIN -> credential.getId();
        };
        FetchType newFetchType = new FetchType(userId, userType);
        fetchTypes.clear();
        fetchTypes.add(newFetchType);
        jsonConverter.saveFetchTypes(fetchTypes);
    }
}
