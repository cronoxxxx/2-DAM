package org.example.tvrestadriansaavedra.common.errors;

public class SeriesNotFoundException extends RuntimeException {
    public SeriesNotFoundException(String message) {
        super(message);
    }
}

