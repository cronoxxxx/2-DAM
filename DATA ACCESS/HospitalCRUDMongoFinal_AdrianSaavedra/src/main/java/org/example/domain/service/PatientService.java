package org.example.domain.service;

import jakarta.inject.Inject;
import org.example.dao.repositories.mongo.PatientRepository;

public class PatientService {
    private final PatientRepository patientRepository;
    @Inject
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }
}
