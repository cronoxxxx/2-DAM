package org.example.loginspring_adriansaavedra.common.errors;

public class PlayerAlreadyExistsException extends RuntimeException{
    public PlayerAlreadyExistsException(String message) {
        super(message);
    }
}
