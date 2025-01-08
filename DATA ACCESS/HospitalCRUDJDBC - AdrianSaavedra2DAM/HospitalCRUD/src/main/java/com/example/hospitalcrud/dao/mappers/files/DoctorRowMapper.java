package com.example.hospitalcrud.dao.mappers.files;

import com.example.hospitalcrud.dao.model.Doctor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("files")
public class DoctorRowMapper {
    public Doctor fromString(String s) {
        String[] parts = s.split(";");
        return new Doctor(
                Integer.parseInt(parts[0].trim()),
                parts[1].trim(),
                parts[2].trim()
        );
    }
}
