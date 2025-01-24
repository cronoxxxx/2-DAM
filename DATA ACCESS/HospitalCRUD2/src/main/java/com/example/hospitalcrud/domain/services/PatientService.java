package com.example.hospitalcrud.domain.services;

import com.example.hospitalcrud.dao.model.Credential;
import com.example.hospitalcrud.dao.model.MedRecord;
import com.example.hospitalcrud.dao.model.Patient;
import com.example.hospitalcrud.dao.repositories.*;

import com.example.hospitalcrud.domain.errors.PatientHasMedicalRecordsException;
import com.example.hospitalcrud.domain.model.PatientUI;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service

public class PatientService {
    private final PatientRepository patientRepository;
    private final PaymentRepository paymentRepository;
    private final MedRecordRepository medRecordRepository;
    private final MedicationRepository medicationRepository;
    private final AppointmentRepository appointmentRepository;

    public PatientService(PatientRepository patientRepository,
                          PaymentRepository paymentRepository,
                          MedRecordRepository medRecordRepository,
                          MedicationRepository medicationRepository,
                          AppointmentRepository appointmentRepository) {
        this.patientRepository = patientRepository;
        this.paymentRepository = paymentRepository;
        this.medRecordRepository = medRecordRepository;
        this.medicationRepository = medicationRepository;
        this.appointmentRepository = appointmentRepository;
    }

    public List<PatientUI> getPatients() {
        return patientRepository.findAll().stream()
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
        Patient patient = new Patient(0, patientUI.getName(), patientUI.getPhone(), patientUI.getBirthDate());
        Credential credential = new Credential(patientUI.getUserName(), patientUI.getPassword());
        patient.setCredential(credential);
        return patientRepository.save(patient).getId();
    }

    public void updatePatient(PatientUI patientUI) {
        Patient patient = patientRepository.findById(patientUI.getId())
                .orElseThrow(() -> new EntityNotFoundException("Patient not found"));
        patient.setName(patientUI.getName());
        patient.setPhone(patientUI.getPhone());
        patient.setBirthDate(patientUI.getBirthDate());
        patientRepository.save(patient);
    }
    @Transactional
    public void delete(int id, boolean confirm) {
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