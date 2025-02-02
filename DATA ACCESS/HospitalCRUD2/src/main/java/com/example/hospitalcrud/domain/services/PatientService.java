package com.example.hospitalcrud.domain.services;

import com.example.hospitalcrud.common.FetchType;
import com.example.hospitalcrud.common.UserType;
import com.example.hospitalcrud.common.UserVerification;
import com.example.hospitalcrud.dao.model.Credential;
import com.example.hospitalcrud.dao.model.MedRecord;
import com.example.hospitalcrud.dao.model.Patient;
import com.example.hospitalcrud.dao.repositories.*;
import com.example.hospitalcrud.domain.errors.PatientHasMedicalRecordsException;
import com.example.hospitalcrud.domain.errors.ModifyingPatientExpection;
import com.example.hospitalcrud.domain.model.PatientUI;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service

public class PatientService {
    private final PatientRepository patientRepository;
    private final PaymentRepository paymentRepository;
    private final MedRecordRepository medRecordRepository;
    private final MedicationRepository medicationRepository;
    private final AppointmentRepository appointmentRepository;
    private final UserVerification userVerification;

    public PatientService(PatientRepository patientRepository,
                          PaymentRepository paymentRepository,
                          MedRecordRepository medRecordRepository,
                          MedicationRepository medicationRepository,
                          AppointmentRepository appointmentRepository,
                          UserVerification userVerification
                          ) {
        this.patientRepository = patientRepository;
        this.paymentRepository = paymentRepository;
        this.medRecordRepository = medRecordRepository;
        this.medicationRepository = medicationRepository;
        this.userVerification = userVerification;
        this.appointmentRepository = appointmentRepository;
    }

    public List<PatientUI> getPatients() {
        FetchType currentUser = userVerification.getCurrentUser();
        if (currentUser == null) {
            return List.of();
        }
        List<Patient> patients = null;
        if (currentUser.getType() == UserType.ADMIN || currentUser.getType() == UserType.DOCTOR) {
            patients = patientRepository.findAll();
        } else if (currentUser.getType() == UserType.PATIENT) {
            patients = Collections.singletonList(patientRepository.findById(currentUser.getId())
                    .orElse(null));
        }
        if (patients == null) {
            return List.of();
        }
        return patients.stream()
                .map(patient -> {
                    Double totalPaid = paymentRepository.getPaymentsByPatient(patient.getId());
                    return new PatientUI(
                            patient.getId(),
                            totalPaid != null ? totalPaid.intValue() : 0,
                            patient.getName(),
                            patient.getPhone(),
                            patient.getCredential() != null ? patient.getCredential().getUserName() : null,
                            null,
                            patient.getBirthDate()
                    );
                })
                .toList();
    }

    public int addPatient(PatientUI patientUI) {
        FetchType currentUser = userVerification.getCurrentUser();
        Patient patient = new Patient(0, patientUI.getName(), patientUI.getPhone(), patientUI.getBirthDate());
        if (currentUser.getType() == UserType.PATIENT) {
            throw new ModifyingPatientExpection("Patients cannot add new patients.");
        }
        Credential credential = new Credential(patientUI.getUserName(), patientUI.getPassword());
        patient.setCredential(credential);
        int newPatientId = patientRepository.save(patient).getId();

        return newPatientId;
    }

    public void updatePatient(PatientUI patientUI) {
        FetchType currentUser = userVerification.getCurrentUser();

        Patient patient = patientRepository.findById(patientUI.getId())
                .orElseThrow(() -> new EntityNotFoundException("Patient not found"));
        if (currentUser.getType() == UserType.PATIENT) {
            throw new ModifyingPatientExpection("Patients cannot update new patients.");
        }
        patient.setName(patientUI.getName());
        patient.setPhone(patientUI.getPhone());
        patient.setBirthDate(patientUI.getBirthDate());
        patientRepository.save(patient);
    }

    @Transactional
    public void delete(int id, boolean confirm) {
        FetchType currentUser = userVerification.getCurrentUser();
        if (currentUser.getType() != UserType.ADMIN) {
            throw new ModifyingPatientExpection("Only administrators can delete patients.");
        }
        List<MedRecord> patientRecords = medRecordRepository.findByPatientId(id);
        if (!confirm && !patientRecords.isEmpty()) {
            throw new PatientHasMedicalRecordsException("Patient has medical records. Are you sure you want to delete?");
        }
        paymentRepository.deleteByPatientId(id);
        appointmentRepository.deleteByPatientId(id);
        medicationRepository.deleteByPatientId(id);
        medRecordRepository.deleteByPatientId(id);
        patientRepository.deleteById(id);

    }
}