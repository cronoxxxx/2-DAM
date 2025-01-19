package com.example.hospitalcrud.dao.repositories;

import com.example.hospitalcrud.dao.model.MedRecord;

import java.util.List;
import java.util.Optional;

public interface MedRecordRepository {

    List<MedRecord> findAll();

    int add(MedRecord medRecord);

    void update(MedRecord medRecord);
    List<MedRecord> findByPatientId(int patientId);
    void delete(MedRecord medRecord);
    void delete(int patientId);
}
