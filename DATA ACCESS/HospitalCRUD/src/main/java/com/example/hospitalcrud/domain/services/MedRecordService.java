package com.example.hospitalcrud.domain.services;

import com.example.hospitalcrud.dao.model.MedRecord;
import com.example.hospitalcrud.dao.model.Medication;
import com.example.hospitalcrud.dao.repositories.MedRecordRepository;
import com.example.hospitalcrud.domain.model.MedRecordUI;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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
        List<MedRecord> allMedRecords = medRecordRepository.findAll();


        return allMedRecords.stream()
                .filter(medRecord -> medRecord.getIdPatient() == idPatient)
                .map(this::convertToMedRecordUI)
                .collect(Collectors.toList());

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
                .idPatient(medRecord.getIdPatient())
                .idDoctor(medRecord.getIdDoctor())
                .medications(medRecord.getMedications().stream()
                        .map(Medication::getMedicationName)
                        .toList())
                .build();
    }

    private MedRecord convertToMedRecord(MedRecordUI medRecordUI) {
        return MedRecord.builder()
                .id(medRecordUI.getId())
                .idPatient(medRecordUI.getIdPatient())
                .idDoctor(medRecordUI.getIdDoctor())
                .diagnosis(medRecordUI.getDescription())
                .date(LocalDate.parse(medRecordUI.getDate()))
                .medications(convertToMedications(medRecordUI.getMedications(), medRecordUI.getId()))
                .build();
    }

    private List<Medication> convertToMedications(List<String> medicationNames, int medRecordId) {
        return medicationNames.stream()
                .map(name -> new Medication(0, name, medRecordId))
                .toList();
    }
}