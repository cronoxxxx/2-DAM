package com.example.hospitalcrud.dao.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NamedQuery;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "patient_payments")

@NamedQuery(name = "Payment.deleteByPatientId", query = "DELETE FROM Payment p WHERE p.patient.id = :patientId")
@NamedQuery(name = "Payment.getPaymentsByPatient", query = "SELECT SUM(p.amount) FROM Payment p WHERE p.patient.id = :patientId")


@Builder
public class Payment {
    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @Column(name = "amount", nullable = false)
    private double amount;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private int id;

    @Column(name = "payment_date")
    private LocalDate paymentDate;

}
