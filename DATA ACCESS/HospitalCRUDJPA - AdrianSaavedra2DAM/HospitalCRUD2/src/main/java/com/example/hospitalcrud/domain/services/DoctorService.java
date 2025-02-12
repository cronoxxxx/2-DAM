package com.example.hospitalcrud.domain.services;

import com.example.hospitalcrud.dao.model.Doctor;
import com.example.hospitalcrud.dao.repositories.DoctorRepository;
import com.example.hospitalcrud.domain.model.DoctorUI;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DoctorService {
//    private final DoctorRepository doctorRepository;
//    public DoctorService(DoctorRepository doctorRepository) {
//        this.doctorRepository = doctorRepository;
//    }

    public List<DoctorUI> getAll() {
//        List<Doctor> doctors = doctorRepository.getAll();
//        List<DoctorUI> doctorUIs = new ArrayList<>();
//        for (Doctor doctor : doctors) {
//            doctorUIs.add(new DoctorUI(doctor.getDoctor_id(), doctor.getName()));
//        }
//        return doctorUIs;
        return List.of();
    }
}
