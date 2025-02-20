package com.example.playersapp_adriansaavedra.ui.pantallaAgregarFavoritos

sealed class AddFavoritesEvent {
    data class UpdateUserId(val userId: Int) : AddFavoritesEvent()
    data class OnNameChange(val value: String) : AddFavoritesEvent()
    data class AddFavoritePlayer(val userId: Int, val playerName: String) : AddFavoritesEvent()
    data object AvisoVisto : AddFavoritesEvent()
}