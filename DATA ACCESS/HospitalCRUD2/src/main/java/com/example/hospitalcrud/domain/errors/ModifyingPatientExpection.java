package com.example.hospitalcrud.domain.errors;

public class ModifyingPatientExpection extends RuntimeException {
    public ModifyingPatientExpection(String message) {
        super(message);
    }
}
