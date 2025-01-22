package com.example.hospitalcrud.dao.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.NamedQuery;
@Data
@AllArgsConstructor
@Entity
@Table(name = "doctors")
@NamedQuery(name="Doctor.getAll", query="SELECT d FROM Doctor d")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doctor_id", nullable = false)
    private int doctor_id;
    @Column(name = "name", nullable = false, length = 50)
    private String name;
    @Column(name = "specialization", nullable = false, length = 100)
    private String specialization;
    @Column(name = "phone", length = 20)
    private String phone;

    public Doctor() {

    }
}
