package com.example.hospitalcrud.domain.services;

import com.example.hospitalcrud.dao.model.MedRecord;
import com.example.hospitalcrud.dao.model.Medication;
import com.example.hospitalcrud.dao.model.Patient;
import com.example.hospitalcrud.dao.repositories.MedRecordRepository;
import com.example.hospitalcrud.domain.model.MedRecordUI;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MedRecordService {
    private final MedRecordRepository medRecordRepository;

    public MedRecordService(MedRecordRepository medRecordRepository) {
        this.medRecordRepository = medRecordRepository;
    }

    public void update(MedRecordUI medRecordUI) {
        MedRecord medRecord = convertToMedRecord(medRecordUI);
        medRecordRepository.update(medRecord);
    }

    public List<MedRecordUI> getAll(int idPatient) {
        List<MedRecord> patientMedRecords = medRecordRepository.findByPatientId(idPatient);

        return patientMedRecords.stream()
                .map(this::convertToMedRecordUI)
                .toList();
    }

    public int add(MedRecordUI medRecordUI) {
        MedRecord medRecord = convertToMedRecord(medRecordUI);
        return medRecordRepository.add(medRecord);
    }

    public void delete(int id) {
        MedRecord medRecord = deleteMedRecord(id);
        medRecordRepository.delete(medRecord);
    }
    private MedRecord deleteMedRecord(int id){
        return medRecordRepository.findAll().stream().filter(medRecord -> medRecord.getId() == id).findFirst().orElse(null);
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