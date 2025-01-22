package com.example.hospitalcrud.dao.repositories;

import com.example.hospitalcrud.dao.model.MedRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedRecordRepository extends JpaRepository<MedRecord, Integer> {
    List<MedRecord> findByPatientId(int patientId);
    void deleteByPatientId( int patientId);
}