package com.example.examfirstmoviles_adriansaavedra.ui.pantallaJugadoresEquipo

import com.example.examfirstmoviles_adriansaavedra.domain.modelo.Equipo
import com.example.examfirstmoviles_adriansaavedra.domain.modelo.Jugador

data class CombinadoState (
    val players : List<Jugador> = emptyList(),
    val aviso : String?=null
)