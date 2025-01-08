package com.example.buzonfxspring_adriansaavedra.domain.model;

import lombok.*;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Data
public class GrupoPublico {
    private List<Usuario> participantes;
    private String nombre;
    private String password;
    private Usuario administrador;
    private LocalDateTime fechaCreacion;

    public GrupoPublico(String nombre, String password, Usuario administrador) {
        this.participantes = new ArrayList<>();
        this.nombre = nombre;
        this.password = password;
        this.administrador = administrador;
        this.fechaCreacion = LocalDateTime.now();
    }
}
