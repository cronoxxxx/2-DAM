package com.example.examfirstmoviles_adriansaavedra.ui.pantallaMomentos

import com.example.examfirstmoviles_adriansaavedra.domain.modelo.Equipo

data class MomentosState (
    val equipos : List<Equipo> = emptyList(),
    val aviso : String?=null
)