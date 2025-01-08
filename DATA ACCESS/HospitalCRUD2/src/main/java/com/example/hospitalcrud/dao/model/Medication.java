package com.example.hospitalcrud.dao.model;

import jakarta.persistence.*;
import lombok.*;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "prescribed_medications")


public class Medication {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prescription_id")
    private int id;

    @Column(name = "medication_name", length = 100)
    private String medicationName;

    @ManyToOne
    @JoinColumn(name = "record_id", nullable = false)
    private MedRecord medRecord;
}
