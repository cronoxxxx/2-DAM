package com.example.finalexamenmoviles_adriansaavedra.ui.pantallaAlumnos

sealed class AlumnosEvent {
    data object AvisoVisto : AlumnosEvent()
    data class AlumnoSelected (val name : String) : AlumnosEvent()
    data object GetAlumnos : AlumnosEvent()
}