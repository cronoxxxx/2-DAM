package com.example.playersapp_adriansaavedra.ui.pantallaAgregarFavoritos

import com.example.playersapp_adriansaavedra.ui.common.UiEvent

data class AddFavoritesState(
    val userId: Int = 0,
    val playerName: String = "",
    val aviso: UiEvent? = null,
    val isLoading: Boolean = false

)
