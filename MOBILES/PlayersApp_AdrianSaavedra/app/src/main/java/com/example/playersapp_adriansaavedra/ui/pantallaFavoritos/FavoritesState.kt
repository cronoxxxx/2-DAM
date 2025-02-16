package com.example.playersapp_adriansaavedra.ui.pantallaFavoritos

import com.example.playersapp_adriansaavedra.domain.model.Player
import com.example.playersapp_adriansaavedra.ui.common.UiEvent

data class FavoritesState(
    val favoritePlayers: List<Player> = emptyList(),
    val isLoading: Boolean = false,
    val aviso: UiEvent? = null,
    val selectedPlayerId: Int = -1
)