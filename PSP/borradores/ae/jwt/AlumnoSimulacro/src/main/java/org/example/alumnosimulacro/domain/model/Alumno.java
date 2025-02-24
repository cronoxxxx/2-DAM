package org.example.alumnosimulacro.domain.model;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Alumno {
    private String nombre;
    private int id;
    List<Asignatura> asignaturas;
}
