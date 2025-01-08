package com.example.hospitalcrud.dao.repositories.jpa.impl;

import com.example.hospitalcrud.dao.model.Medication;
import com.example.hospitalcrud.dao.repositories.MedicationRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class JPAMedicationRepository implements MedicationRepository {
    @Override
    public List<Medication> findByRecordId(int recordId) {
        return List.of();
    }

    @Override
    public void addMedication(int recordId, Medication medication) {

    }

    @Override
    public void deleteMedicationsByRecordId(int recordId) {

    }

    @Override
    public void deleteMedicationsByPatientId(int patientId) {

    }
}
