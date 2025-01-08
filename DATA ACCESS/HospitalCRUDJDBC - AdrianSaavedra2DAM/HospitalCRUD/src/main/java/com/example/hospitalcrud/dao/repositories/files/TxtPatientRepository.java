package com.example.hospitalcrud.dao.repositories.files;
import com.example.hospitalcrud.config.Configuration;
import com.example.hospitalcrud.dao.mappers.files.PatientRowMapper;
import com.example.hospitalcrud.dao.model.Patient;
import com.example.hospitalcrud.dao.repositories.PatientRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

import java.io.*;
import java.nio.file.*;

import java.util.*;
import static java.nio.file.StandardOpenOption.*;

@Profile("this")
@Repository
public class TxtPatientRepository implements PatientRepository {
    private final Path patientsFile;
    private final PatientRowMapper patientRowMapper;


    public TxtPatientRepository(PatientRowMapper patientRowMapper) {
        Configuration config = Configuration.getInstance();
        this.patientsFile = Paths.get(config.getProperty("pathPatients"));
        this.patientRowMapper = patientRowMapper;
    }

    @Override
    public List<Patient> getAll() {
        List<Patient> patients = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(patientsFile)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.isEmpty()) {
                    Patient patient = patientRowMapper.fromString(line);
                    patients.add(patient);
                }
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Error reading patients file", e);
        }
        return patients;
    }


    private Patient get(int id) {
        return getAll().stream()
                .filter(patient -> patient.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public int add(Patient patient) {
        if (get(patient.getId()) == null) {
            String patientData = "\n" + patient;
            try (BufferedWriter writer = Files.newBufferedWriter(patientsFile, APPEND)) {
                writer.write(patientData);
                return patient.getId();
            } catch (IOException e) {
                throw new IllegalArgumentException("Error saving patient", e);
            }
        } else {
            throw new IllegalArgumentException("Patient ID already exists");
        }
    }

    @Override
    public void update(Patient patient) {
        List<Patient> patients = getAll();
        List<String> updatedLines = patients.stream()
                .map(p -> p.getId() == patient.getId() ? patient : p)
                .map(Patient::toString)
                .toList();

        writeLinesToFile(updatedLines);
    }

    @Override
    public void delete(int patientId, boolean confirm) {
        if (get(patientId) != null) {
            List<String> lines;
            try {
                lines = Files.readAllLines(patientsFile);
                lines.removeIf(line -> line.startsWith(patientId + ";"));
                writeLinesToFile(lines);
            } catch (IOException e) {
                throw new IllegalArgumentException("Error deleting patient", e);
            }
        } else {
            throw new IllegalArgumentException("Patient not found");
        }
    }

    private void writeLinesToFile(List<String> lines) {
        try (BufferedWriter writer = Files.newBufferedWriter(patientsFile, TRUNCATE_EXISTING, WRITE)) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Error writing to patients file", e);
        }
    }
}