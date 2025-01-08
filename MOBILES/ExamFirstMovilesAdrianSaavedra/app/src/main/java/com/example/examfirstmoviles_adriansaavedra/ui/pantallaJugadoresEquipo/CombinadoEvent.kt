package com.example.examfirstmoviles_adriansaavedra.ui.pantallaJugadoresEquipo

sealed class CombinadoEvent {
    data class GetPlayers(val id : Int) : CombinadoEvent()
    data object AvisoVisto : CombinadoEvent()
}