package org.example.loginspring_adriansaavedra.common.errors;

public class UserValidatorException extends RuntimeException {
    public UserValidatorException(String message) {
        super(message);
    }
}
