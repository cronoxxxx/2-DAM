package org.example.loginspring_adriansaavedra.common.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionHandlers extends ResponseEntityExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFoundException(UserNotFoundException ex) {
        String message = ex.getMessage();
        return buildResponseEntity(new ApiError(message, HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(PlayerNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFoundException(PlayerNotFoundException ex) {
        String message = ex.getMessage();
        return buildResponseEntity(new ApiError(message, HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(PlayerAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleAlreadyExistsException(PlayerAlreadyExistsException ex) {
        String message = ex.getMessage();
        return buildResponseEntity(new ApiError(message, HttpStatus.BAD_REQUEST));
    }

    private ResponseEntity<ApiError> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.status());
    }
}
