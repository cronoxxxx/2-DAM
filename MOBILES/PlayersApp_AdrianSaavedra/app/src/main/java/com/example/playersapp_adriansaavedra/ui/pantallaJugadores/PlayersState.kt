package com.example.playersapp_adriansaavedra.ui.pantallaJugadores

import com.example.playersapp_adriansaavedra.domain.model.Player
import com.example.playersapp_adriansaavedra.ui.common.UiEvent

data class PlayersState(
    val players: List<Player> = emptyList(),
    val isLoading: Boolean = false,
    val aviso: UiEvent? = null
)