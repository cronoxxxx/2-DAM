package com.example.hospitalcrud.dao.repositories;

import com.example.hospitalcrud.dao.model.MedRecord;

import java.util.List;

public interface MedRecordRepository {

    List<MedRecord> findAll();

    int add(MedRecord medRecord);

    void update(MedRecord medRecord);

    void delete(MedRecord medRecord);
    void delete(int patientId);
}
