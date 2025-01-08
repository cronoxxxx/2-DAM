package com.example.hospitalcrud.dao.repositories;

import com.example.hospitalcrud.dao.model.Patient;

import java.util.List;
import java.util.Map;

public interface PatientRepository {
    List<Patient> getAll();
    int add(Patient patient);
    void delete(int id, boolean confirm);
    void update(Patient patient);
}
