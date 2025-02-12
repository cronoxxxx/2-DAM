package com.example.hospitalcrud.ui;

import com.example.hospitalcrud.domain.model.PatientUI;
import com.example.hospitalcrud.domain.services.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RestPatient {
    private final PatientService patientService;

    public RestPatient(PatientService patientService) {
        this.patientService = patientService;
    }

    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @GetMapping("/patients")
    public List<PatientUI> getPatients() {
        return patientService.getPatients();
    }

    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @PostMapping("/patients")
    public int addPatient(@RequestBody PatientUI patientUI) {
        return patientService.addPatient(patientUI);

    }
    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @PutMapping("/patients")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePatient(@RequestBody PatientUI patientUI) {
        patientService.updatePatient(patientUI);
    }

    //Delete
    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @DeleteMapping("/patients/{patientId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePatient(@PathVariable int patientId, @RequestParam(required = false) boolean confirm) {

        patientService.delete(patientId, confirm);
    }

}
