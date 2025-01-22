package com.example.hospitalcrud.dao.repositories;

import com.example.hospitalcrud.dao.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {
    // No additional methods needed
}
