package com.example.finalexamenmoviles_adriansaavedra.ui.pantallaAlumnos

import com.example.finalexamenmoviles_adriansaavedra.data.remote.model.Alumno
import com.example.finalexamenmoviles_adriansaavedra.ui.common.UiEvent

data class AlumnosState (
    val aviso : UiEvent?=null,
    val isLoading : Boolean = false,
    val alumnos : List<Alumno> = emptyList(),
    val selectedName : String = ""
)