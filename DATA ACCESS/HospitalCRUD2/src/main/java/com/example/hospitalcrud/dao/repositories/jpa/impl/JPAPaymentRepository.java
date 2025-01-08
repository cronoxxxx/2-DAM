package com.example.hospitalcrud.dao.repositories.jpa.impl;

import com.example.hospitalcrud.dao.model.Payment;
import com.example.hospitalcrud.dao.repositories.PaymentRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class JPAPaymentRepository implements PaymentRepository {
    @Override
    public List<Payment> getAllPayments() {
        return List.of();
    }

    @Override
    public void delete(int patientId) {

    }
}
