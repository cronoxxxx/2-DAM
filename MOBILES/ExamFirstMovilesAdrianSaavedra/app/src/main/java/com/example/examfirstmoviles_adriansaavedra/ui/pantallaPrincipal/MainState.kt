package com.example.examfirstmoviles_adriansaavedra.ui.pantallaPrincipal

import com.example.examfirstmoviles_adriansaavedra.domain.modelo.Equipo

data class MainState (
    val equipos : List<Equipo> = emptyList(),
    val aviso : String?=null
)