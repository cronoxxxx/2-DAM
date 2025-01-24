package com.example.hospitalcrud.dao.repositories;

import com.example.hospitalcrud.dao.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface AppointmentRepository extends JpaRepository<Appointment,Integer> {
    @Modifying
    void deleteByPatientId(int patientId);

}
