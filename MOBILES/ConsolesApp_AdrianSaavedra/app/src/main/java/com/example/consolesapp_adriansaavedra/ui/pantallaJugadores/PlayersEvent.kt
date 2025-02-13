package com.example.consolesapp_adriansaavedra.ui.pantallaJugadores

sealed class PlayersEvent {
    data class SelectConsole(val playerId: Int) : PlayersEvent()
    data object GetPlayers : PlayersEvent()
    data object AvisoVisto : PlayersEvent()
}