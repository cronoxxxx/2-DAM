package com.example.hospitalcrud.dao.repositories.jdbc.utils;

public class SQLQueries {

    public static final String ADD_PATIENT = "INSERT INTO patients (name, date_of_birth, phone) VALUES (?, ?, ?)";
    public static final String ADD_USER_LOGIN = "INSERT INTO user_login (username, password, patient_id) VALUES (?, ?, ?)";
    public static final String GET_ALL_PATIENTS = "SELECT * FROM patients";
    public static final String UPDATE_PATIENT = "UPDATE patients SET name = ?, date_of_birth = ?, phone = ? WHERE patient_id = ?";
    public static final String DELETE_USER_LOGIN = "DELETE FROM user_login WHERE patient_id = ?";
    public static final String DELETE_PATIENT = "DELETE FROM patients WHERE patient_id = ?";
    public static final String GET_ALL_PATIENTS_WITH_PAYMENTS = "SELECT patient_id, COALESCE(SUM(amount), 0) as total_paid FROM patient_payments GROUP BY patient_id";
    public static final String GET_ALL_MEDICAL_RECORDS_WITH_MEDICATIONS = "SELECT mr.*, pm.medication_name FROM medical_records mr LEFT JOIN prescribed_medications pm ON mr.record_id = pm.record_id ORDER BY mr.record_id";
    public static final String ADD_MEDICAL_RECORD_WITH_MEDICATIONS = "INSERT INTO medical_records (patient_id, doctor_id, diagnosis, admission_date) VALUES (?, ?, ?, ?)";
    public static final String ADD_PRESCRIBED_MEDICATIONS = "INSERT INTO prescribed_medications (record_id, medication_name) VALUES (?, ?)";
    public static final String UPDATE_MED_RECORD = "UPDATE medical_records SET diagnosis = ?, admission_date = ?, doctor_id = ? WHERE record_id = ?";
    public static final String INSERT_MEDICATION = "INSERT INTO prescribed_medications (record_id, medication_name) VALUES (?, ?)";
    public static final String DELETE_MEDICATIONS = "DELETE FROM prescribed_medications WHERE record_id = ?";
    public static final String DELETE_MED_RECORD = "DELETE FROM medical_records WHERE record_id = ?";
    public static final String DELETE_MED_RECORDS_BY_PATIENT_ID = "DELETE FROM medical_records WHERE patient_id = ?";
    public static final String DELETE_MEDICATIONS_BY_PATIENT_ID = "DELETE FROM prescribed_medications WHERE record_id IN (SELECT record_id FROM medical_records WHERE patient_id = ?)";
    public static final String DELETE_PAYMENTS_BY_PATIENT_ID =  "DELETE FROM patient_payments WHERE patient_id = ?";

    public static final String DELETE_APPOINTMENTS_BY_PATIENT_ID = "DELETE FROM appointments WHERE patient_id = ?";
}
