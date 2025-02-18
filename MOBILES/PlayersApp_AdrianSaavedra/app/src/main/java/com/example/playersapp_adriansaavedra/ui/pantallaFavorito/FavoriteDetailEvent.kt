package com.example.playersapp_adriansaavedra.ui.pantallaFavorito

sealed class FavoriteDetailEvent {
    data class GetFavPlayer(val credentialId: Int, val playerId: Int) : FavoriteDetailEvent()
    data class DeleteFavPlayer(val credentialId: Int, val playerId: Int) : FavoriteDetailEvent()
    data class UpdateUserId(val userId: Int) : FavoriteDetailEvent()
    data object AvisoVisto : FavoriteDetailEvent()
}