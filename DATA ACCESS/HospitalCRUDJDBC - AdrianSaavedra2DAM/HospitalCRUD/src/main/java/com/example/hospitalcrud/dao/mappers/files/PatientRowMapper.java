package com.example.hospitalcrud.dao.mappers.files;

import com.example.hospitalcrud.dao.model.Patient;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@Profile("this")
public class PatientRowMapper {

    public Patient fromString(String line) {
        String[] parts = line.split(";");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return new Patient(
                Integer.parseInt(parts[0].trim()),
                parts[1].trim(),
                parts[3].trim(), // El teléfono está en la cuarta posición
                LocalDate.parse(parts[2].trim(), formatter)
        );
    }

}
