package com.example.apppersonas_adriansaavedra.ui.pantallaMain

import com.example.apppersonas_adriansaavedra.domain.modelo.Persona

data class MainState(
    val personas: List<Persona> = emptyList(),
    val personasSelected: List<Persona> = emptyList(),
    val selectMode: Boolean = false,
    val aviso: String? = null
)
