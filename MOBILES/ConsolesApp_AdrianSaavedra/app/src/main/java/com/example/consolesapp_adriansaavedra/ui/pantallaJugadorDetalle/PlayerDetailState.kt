package com.example.consolesapp_adriansaavedra.ui.pantallaJugadorDetalle

import com.example.consolesapp_adriansaavedra.domain.model.Player
import com.example.consolesapp_adriansaavedra.ui.common.UiEvent

data class PlayerDetailState(
    val player: Player? = null,
    val isLoading: Boolean = false,
    val aviso: UiEvent? = null
)