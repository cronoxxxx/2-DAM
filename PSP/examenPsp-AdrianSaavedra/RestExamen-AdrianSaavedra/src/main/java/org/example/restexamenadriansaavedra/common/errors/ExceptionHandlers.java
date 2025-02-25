package org.example.restexamenadriansaavedra.common.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
@ControllerAdvice
public class ExceptionHandlers {


    private ResponseEntity<ApiError> buildResponseEntity(ApiError apiError, HttpStatus status) {
        return new ResponseEntity<>(apiError, status);
    }

    @ExceptionHandler(NotFound.class)
    public ResponseEntity<ApiError> handleAlreadyExistsException(NotFound ex) {
        String message = ex.getMessage();
        return buildResponseEntity(new ApiError(message), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AlreadyExists.class)
    public ResponseEntity<ApiError> handleNotAvailable(AlreadyExists ex){
        String message = ex.getMessage();
        return buildResponseEntity(new ApiError(message), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Invalid.class)
    public ResponseEntity<ApiError> handleInvalid(Invalid ex){
        String message = ex.getMessage();
        return buildResponseEntity(new ApiError(message), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CropNotAvailable.class)
    public ResponseEntity<ApiError> handleNotAvailable(CropNotAvailable ex){
        String message = ex.getMessage();
        return buildResponseEntity(new ApiError(message), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Rol.class)
    public ResponseEntity<ApiError> handleNotAvailable(Rol ex){
        String message = ex.getMessage();
        return buildResponseEntity(new ApiError(message), HttpStatus.BAD_REQUEST);
    }




}
