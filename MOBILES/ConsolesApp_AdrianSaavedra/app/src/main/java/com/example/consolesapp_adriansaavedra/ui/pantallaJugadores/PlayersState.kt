package com.example.consolesapp_adriansaavedra.ui.pantallaJugadores

import com.example.consolesapp_adriansaavedra.domain.model.Player
import com.example.consolesapp_adriansaavedra.ui.common.UiEvent

data class PlayersState(
    val players: List<Player> = emptyList(),
    val isLoading: Boolean = false,
    val selectedPlayerId: Int = 0,
    val aviso: UiEvent? = null
)