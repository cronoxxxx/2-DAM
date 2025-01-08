package com.example.buzonfxspring_adriansaavedra.domain.errors;

public record ErrorAppDatosNoValidos
        (
                String message
        ) implements ErrorApp{}
