package com.example.hospitalcrud.domain.services;

import com.example.hospitalcrud.dao.model.Credential;
import com.example.hospitalcrud.dao.model.Patient;
import com.example.hospitalcrud.dao.repositories.PatientRepository;

import com.example.hospitalcrud.domain.model.PatientUI;
import jakarta.inject.Inject;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
public class PatientService {
    private final PatientRepository patientRepository;
    //private final PaymentRepository paymentRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
       // this.paymentRepository = paymentRepository;
    }

    public List<PatientUI> getPatients() {
        List<Patient> patients = patientRepository.getAll();

        List<PatientUI> patientUIs = new ArrayList<>();
        for (Patient patient : patients) {
            PatientUI patientUI = new PatientUI(
                    patient.getId(),
                    0, // Placeholder for totalPaid, as payment logic is not implemented here
                    patient.getName(),
                    patient.getPhone(),
                    patient.getCredential() != null ? patient.getCredential().getUserName() : null,
                    null, // Do not include password for security reasons
                    patient.getDateOfBirth()
            );
            patientUIs.add(patientUI);
        }

        return patientUIs;
    }


    public int addPatient(PatientUI patientUI) {
        Patient patient = new Patient(0, patientUI.getName(), patientUI.getPhone(), patientUI.getBirthDate());

        // Credential remains transient and is not persisted in this implementation.
        String username = patientUI.getUserName() != null ? patientUI.getUserName() : null;
        String password = patientUI.getPassword() != null ? patientUI.getPassword() : null;

        Credential credential = new Credential(username, password);
        patient.setCredential(credential);

        return patientRepository.add(patient);
    }

    public void updatePatient(PatientUI patientUI) {
        Patient patient = new Patient(patientUI.getId(), patientUI.getName(), patientUI.getPhone(), patientUI.getBirthDate());

        // Credential remains transient and is not persisted in this implementation.
        String username = patientUI.getUserName() != null ? patientUI.getUserName() : null;
        String password = patientUI.getPassword() != null ? patientUI.getPassword() : null;

        Credential credential = new Credential(username, password);
        patient.setCredential(credential);

        patientRepository.update(patient);
    }

    public void delete(int id, boolean confirm) {
        patientRepository.delete(id, confirm);
    }



}