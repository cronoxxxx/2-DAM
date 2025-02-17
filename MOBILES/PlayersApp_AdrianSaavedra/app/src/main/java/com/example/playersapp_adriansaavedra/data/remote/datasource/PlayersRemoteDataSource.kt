package com.example.playersapp_adriansaavedra.data.remote.datasource

import com.example.playersapp_adriansaavedra.data.remote.services.PlayerService
import com.example.playersapp_adriansaavedra.data.remote.utils.TokenRefresher
import javax.inject.Inject

class PlayersRemoteDataSource @Inject constructor(
    private val playerService: PlayerService,
    tokenRefresher: TokenRefresher
) : BaseApiResponse(tokenRefresher) {
    suspend fun getPlayers() = safeApiCall { playerService.getPlayers() }
    suspend fun getPlayer(id: String) = safeApiCall { playerService.getPlayer(id) }
}