package com.example.finalexamenmoviles_adriansaavedra.ui.pantallaDetalleAlumnos

import com.example.finalexamenmoviles_adriansaavedra.data.remote.model.Alumno
import com.example.finalexamenmoviles_adriansaavedra.ui.common.UiEvent

data class DetailAlumnoState(
    val a: Alumno = Alumno(),
    val isLoading: Boolean = false,
    val aviso: UiEvent? = null
)
