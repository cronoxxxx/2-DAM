package com.example.apppersonas_adriansaavedra.domain.modelo




data class Persona(
    var id : Int = 0,
    val nombre: String = "",
    val email: String = "",
    val estatura : Int = 0,
    val clave : String = "",
    val fechaNacimiento : String = "",
    val genero : String = "",
    val aceptarTerminos : Boolean = false
)
