package org.example.exceptions;

public class FechaImposibleException extends FechaException {
    public FechaImposibleException() {
        super("Este año es bisiesto");
    }
}

