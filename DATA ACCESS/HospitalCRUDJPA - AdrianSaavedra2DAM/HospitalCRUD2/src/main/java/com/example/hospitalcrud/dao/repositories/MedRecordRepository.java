package com.example.hospitalcrud.dao.repositories;

import com.example.hospitalcrud.dao.model.MedRecord;
import org.bson.types.ObjectId;

import java.util.List;

public interface MedRecordRepository {

    List<MedRecord> findAll();

    ObjectId add(MedRecord medRecord);

    void update(MedRecord medRecord);
    List<MedRecord> findByPatientId(ObjectId patientId);
    void delete(ObjectId id);
    void deleteByPatientId(ObjectId patientId);
}
