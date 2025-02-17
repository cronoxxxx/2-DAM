package com.example.playersapp_adriansaavedra.data.remote.datasource

import com.example.playersapp_adriansaavedra.data.remote.services.PlayerService
import javax.inject.Inject

class PlayersRemoteDataSource @Inject constructor(
    private val playerService: PlayerService,
) : BaseApiResponse() {
    suspend fun getPlayers() = safeApiCall { playerService.getPlayers() }
    suspend fun getPlayer(id: String) = safeApiCall { playerService.getPlayer(id) }
}