package com.example.hospitalcrud.ui;


import com.example.hospitalcrud.domain.model.MedRecordUI;
import com.example.hospitalcrud.domain.services.MedRecordService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class RestMedicalRecord {
    private final MedRecordService medRecordService;

    public RestMedicalRecord(MedRecordService medRecordService) {
        this.medRecordService = medRecordService;
    }

    //Find patient medRecords
    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @GetMapping("/patients/{patientID}/medRecords")
    public List<MedRecordUI> getAll(@PathVariable int patientID) {

        return medRecordService.getAll(patientID);
    }

    //Add
    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @PostMapping("/patients/medRecords")
    public int addMedRecord(@RequestBody MedRecordUI medRecordui) {

        return medRecordService.add(medRecordui);

    }

    //Delete
    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @DeleteMapping("/patients/medRecords/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMedRecord(@PathVariable int id) {
        medRecordService.delete(id);
    }

    //Update
    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @PutMapping("/patients/medRecords")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateMedRecord(@RequestBody MedRecordUI medRecordUI) {
        medRecordService.update(medRecordUI);
    }
}

