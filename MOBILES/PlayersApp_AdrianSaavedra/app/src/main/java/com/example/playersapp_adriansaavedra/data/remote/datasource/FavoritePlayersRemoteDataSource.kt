package com.example.playersapp_adriansaavedra.data.remote.datasource

import com.example.playersapp_adriansaavedra.data.remote.services.FavoritePlayerService
import com.example.playersapp_adriansaavedra.data.remote.utils.PlayerNameRequest
import javax.inject.Inject

class FavoritePlayersRemoteDataSource @Inject constructor(
    private val favoritePlayerService: FavoritePlayerService,
) : BaseApiResponse() {
    suspend fun getFavoritePlayers(credentialId: Int) = safeApiCall { favoritePlayerService.getFavoritePlayers(credentialId) }
    suspend fun getFavoritePlayer(credentialId: Int, playerId: Int) = safeApiCall { favoritePlayerService.getFavoritePlayer(credentialId, playerId) }
    suspend fun addFavoritePlayer(credentialId: Int, playerName: PlayerNameRequest) = safeApiCall { favoritePlayerService.addFavoritePlayer(credentialId, playerName) }
    suspend fun deleteFavoritePlayer(credentialId: Int, playerId: Int) = safeApiCall { favoritePlayerService.deleteFavoritePlayer(credentialId, playerId) }
}