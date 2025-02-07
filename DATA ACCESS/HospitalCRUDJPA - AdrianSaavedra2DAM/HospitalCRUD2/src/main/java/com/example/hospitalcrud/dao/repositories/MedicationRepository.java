package com.example.hospitalcrud.dao.repositories;

import com.example.hospitalcrud.dao.model.Medication;

import java.util.List;

public interface MedicationRepository {
    List<Medication> findByRecordId(int recordId);
    void addMedication(int recordId, Medication medication);
    void deleteMedicationsByRecordId(int recordId);
    void deleteMedicationsByPatientId(int patientId);
}
