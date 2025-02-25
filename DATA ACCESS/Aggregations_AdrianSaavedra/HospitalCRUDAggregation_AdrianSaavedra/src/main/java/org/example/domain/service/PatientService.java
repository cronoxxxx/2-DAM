package org.example.domain.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.Document;
import org.example.dao.repositories.PatientDao;

import java.util.List;

@ApplicationScoped
public class PatientService {

    private final PatientDao patientDao;

    @Inject
    public PatientService(PatientDao patientDao) {
        this.patientDao = patientDao;
    }
    public Document getOldestPatient() {
        return patientDao.getOldestPatient();
    }

    public Document getPatientWithHighestPayment() {
        return patientDao.getPatientWithHighestPayment();
    }

    public List<Document> getPatientsWithIbuprofen() {
        return patientDao.getPatientsWithIbuprofen();
    }
}