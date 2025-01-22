package com.example.hospitalcrud.dao.model;

import jakarta.persistence.*;
import lombok.*;


import org.hibernate.annotations.NamedQuery;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "prescribed_medications")
@NamedQuery(name="Medication.deleteByPatientId", query="DELETE FROM Medication m WHERE m.medRecord IN (SELECT mr FROM MedRecord mr WHERE mr.patient.id = :patientId)")
@NamedQuery(name="Medication.deleteByRecordId", query="DELETE FROM Medication m WHERE m.medRecord.id = :medRecordId")
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
