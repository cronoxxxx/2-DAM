package com.example.hospitalcrud.ui.errors;

import com.example.hospitalcrud.domain.errors.DuplicatedUserError;
import com.example.hospitalcrud.domain.errors.ForeignKeyConstraintError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ForeignKeyConstraintError.class)
    public ResponseEntity<ForeignKeyConstraintError> handleForeignKeyConstrayError(ForeignKeyConstraintError ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ForeignKeyConstraintError(ex.getMessage()));
    }

    @ExceptionHandler(DuplicatedUserError.class)
    public ResponseEntity<DuplicatedUserError> handleDuplicatedUserError(DuplicatedUserError ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new DuplicatedUserError(ex.getMessage()));}
}
