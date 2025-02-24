package org.example.alumnosimulacro.ui.controllers;

import org.example.alumnosimulacro.domain.model.Alumno;
import org.example.alumnosimulacro.domain.service.AlumnoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AlumnoController {
    private final AlumnoService alumnoService;

    public AlumnoController(AlumnoService alumnoService) {
        this.alumnoService = alumnoService;
    }

    @PreAuthorize("hasAnyRole('PROFESOR','ALUMNO')")
    @GetMapping("/alumnos")
    public ResponseEntity<List<Alumno>> getAlumnosSinNotas() {
        return ResponseEntity.ok(alumnoService.findAll());
    }

    @PreAuthorize("hasAnyRole('PROFESOR','ALUMNO')")
    @GetMapping("/alumnos/nombres")
    public ResponseEntity<List<String>> getNombres() {
        return ResponseEntity.ok(alumnoService.findNames());
    }

    @PreAuthorize("hasRole('PROFESOR')")
    @PostMapping("/alumnos")
    public ResponseEntity<Alumno> addAlumnoTotal(@RequestBody Alumno alumno) {
        alumnoService.addTotal(alumno);
        return ResponseEntity.status(HttpStatus.CREATED).body(alumno);
    }

    @PreAuthorize("hasRole('PROFESOR')")
    @PostMapping("/alumnos/{nombre}")
    public ResponseEntity<Alumno> addParcial(@PathVariable String nombre) {
        Alumno a = alumnoService.addNoAsignaturas(nombre);
        return ResponseEntity.status(HttpStatus.CREATED).body(a);
    }


}
