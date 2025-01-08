package com.example.hospitalcrud.domain.services;

import com.example.hospitalcrud.dao.model.Credential;
import com.example.hospitalcrud.dao.model.Patient;
import com.example.hospitalcrud.dao.repositories.MedRecordRepository;
import com.example.hospitalcrud.dao.repositories.PatientRepository;
import com.example.hospitalcrud.dao.repositories.jdbc.JDBCPaymentRepository;
import com.example.hospitalcrud.domain.model.PatientUI;
import org.springframework.stereotype.Service;



import java.util.List;
import java.util.Map;


@Service
public class PatientService {
    private final PatientRepository patientRepository;
    private final MedRecordRepository medRecordRepository;
    private final JDBCPaymentRepository paymentRepository;
    public PatientService(PatientRepository patientRepository, MedRecordRepository medRecordRepository, JDBCPaymentRepository paymentRepository) {
        this.patientRepository = patientRepository;
        this.paymentRepository = paymentRepository;
        this.medRecordRepository = medRecordRepository;
    }

    public List<PatientUI> getPatients() {
        List<Patient> patients = patientRepository.getAll();
        Map<Integer, Integer> payments = paymentRepository.getAllPayments();

        return patients.stream().map(patient -> new PatientUI(
                patient.getId(),
                payments.getOrDefault(patient.getId(), 0),
                patient.getName(),
                patient.getPhone(),
                null,
                null,
                patient.getBirthday()
        )).toList();
    }


    public int addPatient(PatientUI patientUI) {
        Patient patient = new Patient(0, patientUI.getName(), patientUI.getPhone(), patientUI.getBirthDate());
        String username = patientUI.getUserName() != null ? patientUI.getUserName() : null;
        String password = patientUI.getPassword() != null ? patientUI.getPassword() : null;
        patient.setCredential(new Credential(username, password));
        return patientRepository.add(patient);
    }

    public void updatePatient(PatientUI patientUI) {
        Patient patient = new Patient(patientUI.getId(), patientUI.getName(), patientUI.getPhone(), patientUI.getBirthDate());
        String username = patientUI.getUserName() != null ? patientUI.getUserName() : null;
        String password = patientUI.getPassword() != null ? patientUI.getPassword() : null;
        patient.setCredential(new Credential(username, password));
        patientRepository.update(patient);
    }

    public void delete(int patientId, boolean confirm) {
        medRecordRepository.delete(patientId);
        patientRepository.delete(patientId, confirm);
    }
}
