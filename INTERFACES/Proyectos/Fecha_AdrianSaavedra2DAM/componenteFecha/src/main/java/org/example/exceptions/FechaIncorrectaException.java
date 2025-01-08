package org.example.exceptions;

public class FechaIncorrectaException extends FechaException {
    public FechaIncorrectaException() {
        super("El mes no tiene 31 dias");
    }
}

