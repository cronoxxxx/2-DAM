package org.example.alumnosimulacro.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Asignatura {
    private String nombre;
    private int nota;
}
