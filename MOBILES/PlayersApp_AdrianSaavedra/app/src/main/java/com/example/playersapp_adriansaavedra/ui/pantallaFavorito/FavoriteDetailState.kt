package com.example.playersapp_adriansaavedra.ui.pantallaFavorito

import com.example.playersapp_adriansaavedra.domain.model.Player
import com.example.playersapp_adriansaavedra.ui.common.UiEvent

data class FavoriteDetailState(
    val userId: Int = 0,
    val player: Player? = null,
    val isLoading: Boolean = false,
    val aviso: UiEvent? = null
)
