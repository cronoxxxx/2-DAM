package com.example.buzonfxspring_adriansaavedra.domain.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Data
public class GrupoPrivado {

    private final List<Usuario> participantes;
    private final String nombre;
    private final Usuario administrador;
    private final LocalDateTime fechaCreacion;

    public GrupoPrivado(String nombre, Usuario administrador) {
        this.participantes = new ArrayList<>();
        this.nombre = nombre;
        this.administrador = administrador;
        this.fechaCreacion = LocalDateTime.now();
    }
}
