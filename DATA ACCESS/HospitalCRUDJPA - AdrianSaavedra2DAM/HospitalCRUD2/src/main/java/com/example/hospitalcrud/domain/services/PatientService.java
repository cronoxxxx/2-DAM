package com.example.hospitalcrud.domain.services;

import com.example.hospitalcrud.dao.model.Credential;
import com.example.hospitalcrud.dao.model.Patient;
import com.example.hospitalcrud.dao.repositories.CredentialRepository;
import com.example.hospitalcrud.dao.repositories.MedRecordRepository;
import com.example.hospitalcrud.dao.repositories.PatientRepository;

import com.example.hospitalcrud.domain.errors.PatientHasMedicalRecordsException;
import com.example.hospitalcrud.domain.model.PatientUI;
import jakarta.inject.Inject;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PatientService {
    private final PatientRepository patientRepository;
    private final CredentialRepository credentialRepository;
    private final MedRecordRepository medRecordRepository;
    private final Map<Integer, ObjectId> patientIdMap = new HashMap<>();
    private int patientIdCounter = 0;

    public PatientService(PatientRepository patientRepository, CredentialRepository credentialRepository, MedRecordRepository medRecordRepository) {
        this.patientRepository = patientRepository;
        this.credentialRepository = credentialRepository;
        this.medRecordRepository = medRecordRepository;
        initializePatientIdMap();
    }

    private void initializePatientIdMap() {
        List<Patient> allPatients = patientRepository.getAll();
        for (Patient patient : allPatients) {
            patientIdCounter++;
            patientIdMap.put(patientIdCounter, patient.getId());
        }
    }

    public List<PatientUI> getPatients() {
        List<Patient> patients = patientRepository.getAll();
        return patients.stream()
                .map(this::convertToPatientUI)
                .collect(Collectors.toList());
    }

    public int addPatient(PatientUI patientUI) {
        Patient patient = new Patient();
        patient.setName(patientUI.getName());
        patient.setBirthDate(patientUI.getBirthDate());
        patient.setPhone(patientUI.getPhone());

        ObjectId patientId = patientRepository.add(patient);
        patientIdCounter++;
        patientIdMap.put(patientIdCounter, patientId);

        Credential credential = new Credential();
        credential.setUsername(patientUI.getUserName());
        credential.setPassword(patientUI.getPassword());
        credential.setPatient(patientId);
        credentialRepository.add(credential);

        return patientIdCounter;
    }

    public void updatePatient(PatientUI patientUI) {
        ObjectId patientId = patientIdMap.get(patientUI.getId());
        if (patientId != null) {
            Patient patient = patientRepository.getById(patientId);
            if (patient != null) {
                patient.setName(patientUI.getName());
                patient.setBirthDate(patientUI.getBirthDate());
                patient.setPhone(patientUI.getPhone());
                patientRepository.update(patient);
            }
        }
    }

    public void delete(int id, boolean confirm) {
        if (!confirm && !medRecordRepository.findByPatientId(patientIdMap.get(id)).isEmpty()) {
            throw new PatientHasMedicalRecordsException("Patient has medical records, Are you sure you want to delete it?");
        }
            ObjectId patientId = patientIdMap.get(id);
            if (patientId != null) {
                patientRepository.delete(patientId);
                patientIdMap.remove(id);
            }
    }

    private int calculateTotalPaid(Patient patient) {
        return patient.getPayments() != null ?
                patient.getPayments().stream().mapToInt(payment -> (int) payment.getAmount()).sum() : 0;
    }

    private PatientUI convertToPatientUI(Patient patient) {
        int patientId = patientIdMap.entrySet().stream()
                .filter(entry -> entry.getValue().equals(patient.getId()))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(0);

        Credential credential = credentialRepository.getByPatientId(patient.getId());

        return new PatientUI(
                patientId,
                calculateTotalPaid(patient),
                patient.getName(),
                patient.getPhone(),
                credential != null ? credential.getUsername() : null,
                null,
                patient.getBirthDate()
        );
    }

    public ObjectId getObjectIdFromMap(int id) {
        return patientIdMap.get(id);
    }

    public int getIntIdFromObjectId(ObjectId objectId) {
        return patientIdMap.entrySet().stream()
                .filter(entry -> entry.getValue().equals(objectId))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(0);
    }
}





