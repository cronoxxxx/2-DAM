package com.example.hospitalcrud.domain.services;

import com.example.hospitalcrud.dao.model.MedRecord;
import com.example.hospitalcrud.dao.model.Medication;
import com.example.hospitalcrud.dao.repositories.MedRecordRepository;
import com.example.hospitalcrud.domain.model.MedRecordUI;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MedRecordService {
    private final MedRecordRepository medRecordRepository;
    private final Map<Integer, ObjectId> medRecordIdMap = new HashMap<>();
    private int medRecordIdCounter = 0;
    private final PatientService patientService;
    private final DoctorService doctorService;

    public MedRecordService(MedRecordRepository medRecordRepository,
                            PatientService patientService,
                            DoctorService doctorService) {
        this.medRecordRepository = medRecordRepository;
        this.patientService = patientService;
        this.doctorService = doctorService;
        initializeMedRecordIdMap();
    }

    private void initializeMedRecordIdMap() {
        List<MedRecord> allMedRecords = medRecordRepository.findAll();
        for (MedRecord medRecord : allMedRecords) {
            medRecordIdCounter++;
            medRecordIdMap.put(medRecordIdCounter, medRecord.getId());
        }
    }

    public void update(MedRecordUI medRecordUI) {
        ObjectId medRecordId = medRecordIdMap.get(medRecordUI.getId());
        if (medRecordId != null) {
            MedRecord medRecord = MedRecord.builder()
                    .id(medRecordId)
                    .idPatient(getObjectIdFromMap(medRecordUI.getIdPatient(), true))
                    .idDoctor(getObjectIdFromMap(medRecordUI.getIdDoctor(), false))
                    .diagnosis(medRecordUI.getDescription())
                    .date(LocalDate.parse(medRecordUI.getDate()))
                    .medications(medRecordUI.getMedications())
                    .build();
            medRecordRepository.update(medRecord);
        }
    }

    public List<MedRecordUI> getAll(int idPatient) {
        ObjectId patientId = getObjectIdFromMap(idPatient, true);
        List<MedRecord> medRecords = medRecordRepository.findByPatientId(patientId);
        return medRecords.stream()
                .map(this::convertToMedRecordUI)
                .collect(Collectors.toList());
    }

    public int add(MedRecordUI medRecordUI) {
        MedRecord medRecord = MedRecord.builder()
                .idPatient(getObjectIdFromMap(medRecordUI.getIdPatient(), true))
                .idDoctor(getObjectIdFromMap(medRecordUI.getIdDoctor(), false))
                .diagnosis(medRecordUI.getDescription())
                .date(LocalDate.parse(medRecordUI.getDate()))
                .medications(medRecordUI.getMedications())
                .build();

        ObjectId medRecordId = medRecordRepository.add(medRecord);
        medRecordIdCounter++;
        medRecordIdMap.put(medRecordIdCounter, medRecordId);

        return medRecordIdCounter;
    }

    public void delete(int id) {
        ObjectId medRecordId = medRecordIdMap.get(id);
        if (medRecordId != null) {
            medRecordRepository.delete(medRecordId);
            medRecordIdMap.remove(id);
        }
    }

    private MedRecordUI convertToMedRecordUI(MedRecord medRecord) {
        int medRecordId = medRecordIdMap.entrySet().stream()
                .filter(entry -> entry.getValue().equals(medRecord.getId()))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(0);

        return MedRecordUI.builder()
                .id(medRecordId)
                .description(medRecord.getDiagnosis())
                .date(medRecord.getDate().toString())
                .idPatient(getIntIdFromObjectId(medRecord.getIdPatient(), true))
                .idDoctor(getIntIdFromObjectId(medRecord.getIdDoctor(), false))
                .medications(medRecord.getMedications())
                .build();
    }

    private ObjectId getObjectIdFromMap(int id, boolean isPatient) {
        if (isPatient) {
            return patientService.getObjectIdFromMap(id);
        } else {
            return doctorService.getObjectIdFromMap(id);
        }
    }

    private int getIntIdFromObjectId(ObjectId objectId, boolean isPatient) {
        if (isPatient) {
            return patientService.getIntIdFromObjectId(objectId);
        } else {
            return doctorService.getIntIdFromObjectId(objectId);
        }
    }
}