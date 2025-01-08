package com.example.hospitalcrud.dao.repositories.files;
import com.example.hospitalcrud.config.Configuration;
import com.example.hospitalcrud.dao.mappers.files.DoctorRowMapper;
import com.example.hospitalcrud.dao.model.Doctor;
import com.example.hospitalcrud.dao.repositories.DoctorRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
@Profile("files")
@Repository
public class TxtDoctorRepository implements DoctorRepository {

    private final DoctorRowMapper doctorRowMapper;
    private final String doctorsFilePath;

    public TxtDoctorRepository(DoctorRowMapper doctorRowMapper) {
        this.doctorRowMapper = doctorRowMapper;
        this.doctorsFilePath = Configuration.getInstance().getProperty("pathDoctors");
    }


    public List<Doctor> getAll() {
        List<Doctor> doctors;
        Path file = Paths.get(doctorsFilePath);
        try {
            List<String> lines = Files.readAllLines(file);
            doctors = lines.stream()
                    .map(doctorRowMapper::fromString)
                    .toList();
        } catch (IOException e) {
            throw new IllegalArgumentException("Error reading patients file", e);
        }
        return doctors;
    }
}
