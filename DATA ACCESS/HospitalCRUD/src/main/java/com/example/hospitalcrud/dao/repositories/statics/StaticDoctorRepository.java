package com.example.hospitalcrud.dao.repositories.statics;

import com.example.hospitalcrud.dao.model.Doctor;
import com.example.hospitalcrud.dao.repositories.DoctorRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
@Repository
@Profile("static")
public class StaticDoctorRepository implements DoctorRepository {
    private static final List <Doctor> doctors;

    static  {
        doctors = Arrays.asList(
                new Doctor(1, "Doctor1","Cirujano"),
                new Doctor(2, "Doctor2","Pediatria"),
                new Doctor(3, "Doctor3","Traumatologia")
        );
    }
    public List<Doctor> getAll() {
        return doctors;
    }

}
