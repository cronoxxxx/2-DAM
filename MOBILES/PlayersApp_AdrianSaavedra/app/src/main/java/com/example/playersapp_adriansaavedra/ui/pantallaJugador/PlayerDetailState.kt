package com.example.playersapp_adriansaavedra.ui.pantallaJugador

import com.example.playersapp_adriansaavedra.domain.model.Player
import com.example.playersapp_adriansaavedra.ui.common.UiEvent

data class PlayerDetailState (
    val player : Player ? = null,
    val isLoading : Boolean = false,
    val aviso : UiEvent? = null
)