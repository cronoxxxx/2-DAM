package com.example.hospitalcrud.dao.repositories;

import com.example.hospitalcrud.dao.model.Patient;
import com.example.hospitalcrud.dao.model.Payment;

import java.util.List;

public interface PaymentRepository {
    List<Payment> getAllPayments();
    void delete(int patientId);
}
