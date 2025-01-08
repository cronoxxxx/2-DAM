package com.example.segundaapp_adriansaavedra.domain.modelo




data class Persona(
    val nombre: String = "",
    val email: String = "",
    val estatura : Int = 0,
    val clave : String = "",
    val fechaNacimiento : String = "",
    val genero : String = "",
    val aceptarTerminos : Boolean = false
)
