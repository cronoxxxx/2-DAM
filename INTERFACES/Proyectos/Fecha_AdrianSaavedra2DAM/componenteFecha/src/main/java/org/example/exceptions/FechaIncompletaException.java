package org.example.exceptions;


public class FechaIncompletaException extends FechaException {
    public FechaIncompletaException() {
        super("La fecha esta incompleta");
    }
}