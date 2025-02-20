package org.example.loginspring_adriansaavedra.common.errors;

public class PlayerNotFoundException extends RuntimeException {
    public PlayerNotFoundException(String message) {
        super(message);
    }
}
