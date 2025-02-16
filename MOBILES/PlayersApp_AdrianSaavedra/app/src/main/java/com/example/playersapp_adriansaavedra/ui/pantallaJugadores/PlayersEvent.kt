package com.example.playersapp_adriansaavedra.ui.pantallaJugadores

sealed class PlayersEvent {

    data object OnGetPlayers : PlayersEvent()
    data object OnAvisoVisto : PlayersEvent()
}