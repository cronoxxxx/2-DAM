package com.example.hospitalcrud.dao.repositories.jpa.impl;

import com.example.hospitalcrud.dao.model.MedRecord;
import com.example.hospitalcrud.dao.repositories.MedRecordRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class JPAMedRecordRepository implements MedRecordRepository {
    @Override
    public List<MedRecord> findAll() {
        return List.of();
    }

    @Override
    public int add(MedRecord medRecord) {
        return 0;
    }

    @Override
    public void update(MedRecord medRecord) {

    }

    @Override
    public List<MedRecord> findByPatientId(int patientId) {
        return List.of();
    }

    @Override
    public void delete(MedRecord medRecord) {

    }

    @Override
    public void delete(int patientId) {

    }
}
