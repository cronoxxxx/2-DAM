package com.example.hospitalcrud.domain.services;

import com.example.hospitalcrud.common.FetchType;
import com.example.hospitalcrud.common.UserType;
import com.example.hospitalcrud.common.UserVerification;
import com.example.hospitalcrud.dao.model.MedRecord;
import com.example.hospitalcrud.dao.model.Medication;
import com.example.hospitalcrud.dao.model.Patient;
import com.example.hospitalcrud.dao.repositories.MedRecordRepository;
import com.example.hospitalcrud.dao.repositories.MedicationRepository;
import com.example.hospitalcrud.domain.errors.ModifyingMedRecordException;
import com.example.hospitalcrud.domain.model.MedRecordUI;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class MedRecordService {
    private final MedRecordRepository medRecordRepository;
    private final MedicationRepository medicationRepository;
    private final UserVerification userVerification;

    public MedRecordService(MedRecordRepository medRecordRepository, MedicationRepository medicationRepository, UserVerification userVerification) {
        this.userVerification = userVerification;
        this.medRecordRepository = medRecordRepository;
        this.medicationRepository = medicationRepository;
    }

    @Transactional
    public void update(MedRecordUI medRecordUI) {
        FetchType currentUser = userVerification.getCurrentUser();
        MedRecord existingMedRecord = medRecordRepository.findById(medRecordUI.getId())
                .orElse(null);
        if (existingMedRecord == null) {
            return;
        }
        if (currentUser.getType() == UserType.PATIENT) {
            throw new ModifyingMedRecordException("Patients cannot update medical records.");
        }

        if (currentUser.getType() == UserType.DOCTOR && currentUser.getId() != existingMedRecord.getIdDoctor()) {
            throw new ModifyingMedRecordException("Doctors can only update their own medical records.");
        }
        existingMedRecord.setDiagnosis(medRecordUI.getDescription());
        existingMedRecord.setDate(LocalDate.parse(medRecordUI.getDate()));
        existingMedRecord.setIdDoctor(medRecordUI.getIdDoctor());
        existingMedRecord.getMedications().clear();
        medicationRepository.deleteByRecordId(medRecordUI.getId());
        List<Medication> newMedications = convertToMedications(medRecordUI.getMedications(), existingMedRecord);
        existingMedRecord.getMedications().addAll(newMedications);
        medRecordRepository.save(existingMedRecord);
    }

    public List<MedRecordUI> getAll(int idPatient) {
        FetchType currentUser = userVerification.getCurrentUser();
        if (currentUser.getType() == UserType.PATIENT && currentUser.getId() != idPatient) {
            throw new ModifyingMedRecordException("Patients can only view their own medical records.");
        }
        return medRecordRepository.findByPatientId(idPatient).stream()
                .map(this::convertToMedRecordUI)
                .toList();
    }

    public int add(MedRecordUI medRecordUI) {
        FetchType currentUser = userVerification.getCurrentUser();
        if (currentUser.getType() == UserType.PATIENT) {
            throw new ModifyingMedRecordException("Patients cannot add medical records.");
        }
        if (currentUser.getType() == UserType.DOCTOR && currentUser.getId() != medRecordUI.getIdDoctor()) {
            throw new ModifyingMedRecordException("Doctors can only add medical records with themselves as the responsible doctor.");
        }
        MedRecord medRecord = convertToMedRecord(medRecordUI);
        return medRecordRepository.save(medRecord).getId();
    }

    public void delete(int id) {
        FetchType currentUser = userVerification.getCurrentUser();
        MedRecord medRecord = medRecordRepository.findById(id)
                .orElse(null);
        if (medRecord == null) {
            return;
        }

        if (currentUser.getType() == UserType.PATIENT) {
            throw new ModifyingMedRecordException("Patients cannot delete medical records.");
        }

        if (currentUser.getType() == UserType.DOCTOR && currentUser.getId() != medRecord.getIdDoctor()) {
            throw new ModifyingMedRecordException("Doctors can only delete their own medical records.");
        }
        medRecordRepository.deleteById(id);
    }

    private MedRecordUI convertToMedRecordUI(MedRecord medRecord) {
        return MedRecordUI.builder()
                .id(medRecord.getId())
                .description(medRecord.getDiagnosis())
                .date(medRecord.getDate().toString())
                .idPatient(medRecord.getPatient().getId())
                .idDoctor(medRecord.getIdDoctor())
                .medications(medRecord.getMedications().stream()
                        .map(Medication::getMedicationName)
                        .toList())
                .build();
    }

    private MedRecord convertToMedRecord(MedRecordUI medRecordUI) {
        Patient patient = new Patient();
        patient.setId(medRecordUI.getIdPatient());

        MedRecord medRecord = MedRecord.builder()
                .id(medRecordUI.getId())
                .patient(patient)
                .idDoctor(medRecordUI.getIdDoctor())
                .diagnosis(medRecordUI.getDescription())
                .date(LocalDate.parse(medRecordUI.getDate()))
                .build();

        List<Medication> medications = convertToMedications(medRecordUI.getMedications(), medRecord);
        medRecord.setMedications(medications);

        return medRecord;
    }


    private List<Medication> convertToMedications(List<String> medicationNames, MedRecord medRecord) {
        return medicationNames.stream()
                .map(name -> new Medication(0, name, medRecord))
                .toList();
    }


}