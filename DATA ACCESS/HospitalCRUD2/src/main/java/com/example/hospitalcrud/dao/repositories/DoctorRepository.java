package com.example.hospitalcrud.dao.repositories;

import com.example.hospitalcrud.dao.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
    // No additional methods needed
}