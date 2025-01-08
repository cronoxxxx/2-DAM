package com.example.miprimeraaplicacionfx_adriansaavedra.domain.model;

import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



@Getter
@Setter
public class Grupo {
    private List<Usuario> participantes;

    private String nombre;

    private String password;
    private Usuario administrador;

    private boolean publico;

    private LocalDateTime fechaCreacion;

    public Grupo(String nombre, String password, Usuario administrador, boolean publico) {
        this.participantes = new ArrayList<>();
        this.nombre = nombre;
        this.password = password;
        this.administrador = administrador;
        this.fechaCreacion = LocalDateTime.now();
        this.publico = publico;
    }



    @Override
    public String toString() {
        return "Grupo{" +
                "participantes=" + participantes.stream().map(Usuario::getNombre).sorted().toList() +
                ", nombre='" + nombre + '\'' +
                ", password='" + password + '\'' +
                ", administrador=" + administrador.getNombre() +
                ", publico=" + publico +
                ", fechaCreacion=" + fechaCreacion +
                '}';
    }





}
