package com.example.hospitalcrud.dao.repositories;

import com.example.hospitalcrud.dao.model.Credential;

public interface CredentialRepository {
    Credential get(String username);
    void add (Credential credential);
    Credential getByPatientId(int patientId);
    void delete(int id);
}
