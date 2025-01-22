package com.example.hospitalcrud.dao.model;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.hibernate.annotations.NamedQuery;
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "patients")

@NamedQuery(name = "Patient.findAll", query = "SELECT p FROM Patient p")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_id", nullable = false)
    private int id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "date_of_birth")
    private LocalDate birthDate;

    @Column(name = "phone", length = 20)
    private String phone;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, mappedBy = "patient")
    private Credential credential;


    public Patient(int id, String name, String phone, LocalDate birthDate) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return id + ";" + name + ";" + birthDate.format(formatter) + ";" + phone;
    }

    public void setCredential(Credential credential) {
        this.credential = credential;
        credential.setPatient(this);
    }
}