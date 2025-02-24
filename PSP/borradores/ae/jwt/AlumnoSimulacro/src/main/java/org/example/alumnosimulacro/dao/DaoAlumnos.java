package org.example.alumnosimulacro.dao;

import org.example.alumnosimulacro.domain.model.Alumno;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DaoAlumnos {

    private final Database db;


    public DaoAlumnos(Database db) {
        this.db = db;
    }

    public List<Alumno> findAll(){
        return new ArrayList<>(db.getAlumnos());
    }

    public List<String> findAlumnosNombres(){
       return db.getAlumnos().stream().map(Alumno::getNombre).toList();
    }

    public Alumno addAlumnosSinNotas(String alumno){
        Alumno alumno1 = new Alumno(alumno, db.getAutoIncremento(), new ArrayList<>());
        ensureUserDoesNotExist(alumno1.getNombre());
        db.getAlumnos().add(alumno1);
        return alumno1;
    }

    public void addAlumnosTotal(Alumno alumno){
        alumno.setId(db.getAutoIncremento());
        ensureUserDoesNotExist(alumno.getNombre());
        db.getAlumnos().add(alumno);

    }



    private void ensureUserDoesNotExist(String username) {
        boolean userExists = db.getAlumnos().stream()
                .anyMatch(existingUser -> existingUser.getNombre().equals(username));
        if (userExists) {
            throw new RuntimeException("User already exists: " + username);
        }
    }
}
