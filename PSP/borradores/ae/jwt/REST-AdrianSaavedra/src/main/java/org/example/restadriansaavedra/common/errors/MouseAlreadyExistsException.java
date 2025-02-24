package org.example.restadriansaavedra.common.errors;

public class MouseAlreadyExistsException extends RuntimeException{
    public MouseAlreadyExistsException(String message) {
        super(message);
    }
}
