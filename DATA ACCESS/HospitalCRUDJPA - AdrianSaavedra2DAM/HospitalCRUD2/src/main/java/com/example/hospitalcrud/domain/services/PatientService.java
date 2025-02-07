package com.example.hospitalcrud.domain.services;

import com.example.hospitalcrud.dao.model.Credential;
import com.example.hospitalcrud.dao.model.Patient;
import com.example.hospitalcrud.dao.repositories.CredentialRepository;
import com.example.hospitalcrud.dao.repositories.PatientRepository;

import com.example.hospitalcrud.domain.model.PatientUI;
import jakarta.inject.Inject;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientService {
    private final PatientRepository patientRepository;
    private final CredentialRepository credentialRepository;

    public PatientService(PatientRepository patientRepository, CredentialRepository credentialRepository) {
        this.patientRepository = patientRepository;
        this.credentialRepository = credentialRepository;
    }

    public List<PatientUI> getPatients() {
        List<Patient> patients = patientRepository.getAll();
        return patients.stream().map(this::convertToPatientUI).collect(Collectors.toList());
    }

    public int addPatient(PatientUI patientUI) {
        Patient patient = new Patient();
        patient.setName(patientUI.getName());
        patient.setPhone(patientUI.getPhone());
        patient.setBirthDate(patientUI.getBirthDate());

        ObjectId patientId = patientRepository.add(patient);

        if (patientUI.getUserName() != null && patientUI.getPassword() != null) {
            Credential credential = new Credential();
            credential.setUsername(patientUI.getUserName());
            credential.setPassword(patientUI.getPassword());
            credential.setPatient(patientId);
            credentialRepository.add(credential);
        }

        return Integer.parseInt(patientId.toString());
    }

    public void updatePatient(PatientUI patientUI) {
        Patient patient = new Patient();
        patient.setId(new ObjectId(String.valueOf(patientUI.getId())));
        patient.setName(patientUI.getName());
        patient.setPhone(patientUI.getPhone());
        patient.setBirthDate(patientUI.getBirthDate());

        patientRepository.update(patient);

        Credential credential = credentialRepository.getByPatientId(patient.getId());
        if (credential != null && patientUI.getUserName() != null && patientUI.getPassword() != null) {
            credential.setUsername(patientUI.getUserName());
            credential.setPassword(patientUI.getPassword());
            credentialRepository.add(credential);
        }
    }

    public void delete(int id,boolean confirm) {
        ObjectId objectId = new ObjectId(String.valueOf(id));
        patientRepository.delete(objectId);
        Credential credential = credentialRepository.getByPatientId(objectId);
        if (credential != null) {
            credentialRepository.delete(credential.getId());
        }
    }

    private int calculateTotalPaid(Patient patient) {
        return patient.getPayments() != null ?
                patient.getPayments().stream().mapToInt(payment -> (int) payment.getAmount()).sum() : 0;
    }

    private PatientUI convertToPatientUI(Patient patient) {
        int toInt = patient.getId().hashCode();
        Credential credential = credentialRepository.getByPatientId(patient.getId());
        return new PatientUI(
                toInt,
                calculateTotalPaid(patient),
                patient.getName(),
                patient.getPhone(),
                credential != null ? credential.getUsername() : null,
                null,
                patient.getBirthDate()
        );
    }



}
