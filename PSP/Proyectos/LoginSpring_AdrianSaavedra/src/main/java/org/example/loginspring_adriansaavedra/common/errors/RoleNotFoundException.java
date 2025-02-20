package org.example.loginspring_adriansaavedra.common.errors;

public class RoleNotFoundException extends RuntimeException{
    public RoleNotFoundException(String message) {
        super(message);
    }
}
