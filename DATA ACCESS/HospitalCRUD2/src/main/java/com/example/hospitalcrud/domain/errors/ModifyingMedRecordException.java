package com.example.hospitalcrud.domain.errors;

public class ModifyingMedRecordException extends RuntimeException{
    public ModifyingMedRecordException(String message) {
        super(message);
    }
}
