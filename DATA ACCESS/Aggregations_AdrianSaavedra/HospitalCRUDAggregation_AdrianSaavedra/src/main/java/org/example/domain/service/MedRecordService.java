package org.example.domain.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.Document;
import org.example.dao.repositories.MedRecordDao;

import java.util.List;

@ApplicationScoped
public class MedRecordService {

    private final MedRecordDao medRecordDao;
    @Inject
    public MedRecordService(MedRecordDao medRecordDao) {
        this.medRecordDao = medRecordDao;
    }

    public List<Document> getMedRecordsWithPatientInfo(String patientName) {
        return medRecordDao.getMedRecordsWithPatientInfo(patientName);
    }

    public List<Document> getMedicationCountPerMedRecord() {
        return medRecordDao.getMedicationCountPerMedRecord();
    }

    public Document getAverageMedicationsPerMedRecord() {
        return medRecordDao.getAverageMedicationsPerMedRecord();
    }

    public Document getMostPrescribedMedication() {
        return medRecordDao.getMostPrescribedMedication();
    }

    public List<Document> getMostPrescribedMedicationPerPatient() {
        return medRecordDao.getMostPrescribedMedicationPerPatient();
    }

    public Document getDoctorWithMostPrescriptions() {
        return medRecordDao.getDoctorWithMostPrescriptions();
    }
}