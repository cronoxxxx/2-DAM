package org.example.alumnosimulacro.dao;

import lombok.Getter;
import org.example.alumnosimulacro.domain.model.Alumno;
import org.example.alumnosimulacro.domain.model.Usuario;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Getter
@Component
public class Database {

    private final List<Alumno> alumnos;
    private final List<Usuario> usuarios;
    private int autoincremento = 1;
    public Database() {
        this.alumnos = new ArrayList<>();
        this.usuarios = new ArrayList<>();
    }

    public int getAutoIncremento(){
        return autoincremento++;
    }

}
