package com.example.hospitalcrud.dao.repositories.statics;

import com.example.hospitalcrud.dao.model.Patient;
import com.example.hospitalcrud.dao.repositories.PatientRepository;
import lombok.Data;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Profile("static")
@Repository
public class StaticPatientRepository implements PatientRepository {
    private static final List<Patient> patients = new ArrayList<>();
    private int lastId;

    public StaticPatientRepository() {
        patients.add(new Patient(1, "John", "Doe", LocalDate.of(2000, 1, 1)));
        patients.add(new Patient(2, "Juan", "Perez", LocalDate.of(1990, 4, 12)));
        patients.add(new Patient(3, "Pedro", "Garcia", LocalDate.of(1995, 5, 1)));
        patients.add(new Patient(4, "Maria", "Rodriguez", LocalDate.of(1980, 1, 15)));
        patients.add(new Patient(5, "Ana", "Gonzalez", LocalDate.of(1975, 6, 20)));
        patients.add(new Patient(6, "Luis", "Sanchez", LocalDate.of(1960, 7, 25)));
        this.lastId = patients.get(patients.size() - 1).getId();
    }

    public List<Patient> getAll() {
        return patients;
    }

    @Override
    public int add(Patient patient) {
        return 0;
    }

    public void delete(int patientId, boolean confirm) {
    }

    public void update(Patient patient) {

    }
}
