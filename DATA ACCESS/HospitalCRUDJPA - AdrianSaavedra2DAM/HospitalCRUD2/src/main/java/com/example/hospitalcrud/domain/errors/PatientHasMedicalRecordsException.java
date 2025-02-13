package com.example.hospitalcrud.domain.errors;

public class PatientHasMedicalRecordsException extends RuntimeException {
    public PatientHasMedicalRecordsException(String message) {
        super(message);
    }
}
