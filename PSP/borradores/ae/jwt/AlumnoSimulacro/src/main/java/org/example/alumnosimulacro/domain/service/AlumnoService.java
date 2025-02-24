package org.example.alumnosimulacro.domain.service;

import org.example.alumnosimulacro.dao.DaoAlumnos;
import org.example.alumnosimulacro.domain.model.Alumno;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AlumnoService {
    private final DaoAlumnos daoAlumnos;

    public AlumnoService(DaoAlumnos daoAlumnos) {
        this.daoAlumnos = daoAlumnos;
    }

    public List<Alumno> findAll(){
        return daoAlumnos.findAll();
    }

    public List<String> findNames(){
        return daoAlumnos.findAlumnosNombres();
    }

    public Alumno addNoAsignaturas (String alumno){
      return   daoAlumnos.addAlumnosSinNotas(alumno);
    }

    public void addTotal(Alumno alumno){
        daoAlumnos.addAlumnosTotal(alumno);
    }



}
