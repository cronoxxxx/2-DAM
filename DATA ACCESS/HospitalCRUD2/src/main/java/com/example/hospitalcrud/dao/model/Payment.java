package com.example.hospitalcrud.dao.model;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "patient_payments")
@Builder
public class Payment {
    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;
    @Column(name = "amount", nullable = false)
    private int total_paid;
}
