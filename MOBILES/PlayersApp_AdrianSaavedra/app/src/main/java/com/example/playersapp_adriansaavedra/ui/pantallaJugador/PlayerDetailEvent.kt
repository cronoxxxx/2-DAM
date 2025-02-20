package com.example.playersapp_adriansaavedra.ui.pantallaJugador

sealed class PlayerDetailEvent {
    data class GetPlayer(val id: Int) : PlayerDetailEvent()
    data object AvisoVisto : PlayerDetailEvent()
}