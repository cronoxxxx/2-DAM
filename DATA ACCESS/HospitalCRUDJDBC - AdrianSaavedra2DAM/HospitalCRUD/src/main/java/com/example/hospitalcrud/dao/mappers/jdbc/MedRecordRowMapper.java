package com.example.hospitalcrud.dao.mappers.jdbc;

import com.example.hospitalcrud.dao.model.MedRecord;
import com.example.hospitalcrud.dao.model.Medication;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Component
public class MedRecordRowMapper {
    public MedRecord mapRow(ResultSet rs) throws SQLException {
        int recordId = rs.getInt("record_id");
        MedRecord medRecord = new MedRecord();
        medRecord.setId(recordId);
        medRecord.setIdPatient(rs.getInt("patient_id"));
        medRecord.setIdDoctor(rs.getInt("doctor_id"));
        medRecord.setDate(rs.getDate("admission_date").toLocalDate());
        medRecord.setDiagnosis(rs.getString("diagnosis"));
        medRecord.setMedications(new ArrayList<>());

        String medicationName = rs.getString("medication_name");
        if (medicationName != null && !medicationName.isEmpty()) {
            Medication medication = new Medication();
            medication.setMedicationName(medicationName);
            medRecord.getMedications().add(medication);
        }

        return medRecord;
    }
}
