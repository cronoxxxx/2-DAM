package com.example.miprimeraaplicacionfx_adriansaavedra.domain.model;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class Usuario {
    private String nombre;
    private String password;

    public Usuario(String nombre, String password) {
        this.nombre = nombre;
        this.password = password;
    }






}
