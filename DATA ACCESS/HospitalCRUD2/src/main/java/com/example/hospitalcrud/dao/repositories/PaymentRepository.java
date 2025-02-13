package com.example.hospitalcrud.dao.repositories;

import com.example.hospitalcrud.dao.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    Double getPaymentsByPatient(int patientId);
    @Modifying
    void deleteByPatientId( int patientId);
}
