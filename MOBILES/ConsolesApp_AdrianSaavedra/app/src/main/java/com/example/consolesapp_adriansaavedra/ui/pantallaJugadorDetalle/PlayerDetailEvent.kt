package com.example.consolesapp_adriansaavedra.ui.pantallaJugadorDetalle

sealed class PlayerDetailEvent {
    data class GetPlayerConsoles(val id: Int) : PlayerDetailEvent()
    data object AvisoVisto : PlayerDetailEvent()
}