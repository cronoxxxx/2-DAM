package com.example.hospitalcrud.dao.repositories;

import com.example.hospitalcrud.dao.model.Patient;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Map;

public interface PatientRepository {
    List<Patient> getAll();
    ObjectId add(Patient patient);
    void delete(ObjectId id);
    void update(Patient patient);
}
