package com.example.hospitalcrud.domain.services;

import com.example.hospitalcrud.dao.model.Doctor;
import com.example.hospitalcrud.dao.repositories.DoctorRepository;
import com.example.hospitalcrud.domain.model.DoctorUI;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final Map<Integer, ObjectId> doctorIdMap = new HashMap<>();
    private int doctorIdCounter = 0;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
        initializeDoctorIdMap();
    }

    private void initializeDoctorIdMap() {
        List<Doctor> allDoctors = doctorRepository.getAll();
        for (Doctor doctor : allDoctors) {
            doctorIdCounter++;
            doctorIdMap.put(doctorIdCounter, doctor.getDoctor_id());
        }
    }

    public List<DoctorUI> getAll() {
        List<Doctor> doctors = doctorRepository.getAll();
        List<DoctorUI> doctorUIs = doctors.stream()
                .map(this::convertToDoctorUI)
                .collect(Collectors.toList());
        return doctorUIs;
    }

    private DoctorUI convertToDoctorUI(Doctor doctor) {
        int doctorId = doctorIdMap.entrySet().stream()
                .filter(entry -> entry.getValue().equals(doctor.getDoctor_id()))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(0);

        return DoctorUI.builder()
                .id(doctorId)
                .name(doctor.getName())
                .build();
    }

    public ObjectId getObjectIdFromMap(int id) {
        return doctorIdMap.get(id);
    }

    public int getIntIdFromObjectId(ObjectId objectId) {
        return doctorIdMap.entrySet().stream()
                .filter(entry -> entry.getValue().equals(objectId))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(0);
    }
}
