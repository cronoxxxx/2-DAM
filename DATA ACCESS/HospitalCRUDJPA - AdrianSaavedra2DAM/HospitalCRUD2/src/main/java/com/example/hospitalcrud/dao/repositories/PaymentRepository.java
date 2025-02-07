package com.example.hospitalcrud.dao.repositories;

import com.example.hospitalcrud.dao.model.Patient;

import java.util.List;

public interface PaymentRepository {
    List<Patient.Payment> getAllPayments();
    void delete(int patientId);
}
