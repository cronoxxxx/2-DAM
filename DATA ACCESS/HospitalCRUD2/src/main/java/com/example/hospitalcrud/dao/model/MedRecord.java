package com.example.hospitalcrud.dao.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "medical_records")
@NamedQuery(name = "MedRecord.deleteByPatientId", query = "DELETE FROM MedRecord m WHERE m.patient.id = :patientId")

public class MedRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @Column(name = "doctor_id", nullable = false)
    private int idDoctor;

    @Column(name = "diagnosis")
    private String diagnosis;

    @Column(name = "admission_date")
    private LocalDate date;

    @OneToMany(mappedBy = "medRecord",cascade = { CascadeType.PERSIST,CascadeType.REMOVE,CascadeType.MERGE})
    private List<Medication> medications;
}