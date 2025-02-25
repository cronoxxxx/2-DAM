package org.example.restexamenadriansaavedra.common.errors;

public class AlreadyExists extends RuntimeException{
    public AlreadyExists(String message) {
        super(message);
    }
}
