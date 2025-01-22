package com.example.hospitalcrud.dao.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import org.hibernate.annotations.NamedQuery;
@Entity
@Table(name = "appointments")
@NamedQuery(name = "Appointment.deleteByPatientId", query = "DELETE FROM Appointment a WHERE a.patient.id = :patientId")

public class Appointment {
    @Column(name = "appointment_date")
    private LocalDate appointmentDate;
    @Column(name = "doctor_id")
    private Integer doctorId;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_id")
    private Integer appointmentId;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

}
