package com.example.hospitalcrud.dao.repositories;

import com.example.hospitalcrud.dao.model.Credential;
import org.bson.types.ObjectId;

public interface CredentialRepository {
    Credential get(String username);
    void add(Credential credential);
    Credential getByPatientId(ObjectId patientId);
    void deleteByPatientId(ObjectId patientId);
}
