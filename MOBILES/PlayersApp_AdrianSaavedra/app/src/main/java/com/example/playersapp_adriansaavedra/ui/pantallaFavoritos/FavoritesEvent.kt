package com.example.playersapp_adriansaavedra.ui.pantallaFavoritos

sealed class FavoritesEvent {
    data object OnAvisoVisto : FavoritesEvent()
    data class OnGetFavoritePlayers(val id: Int) : FavoritesEvent()
    data class OnPlayerClick(val playerId: Int) : FavoritesEvent()
}