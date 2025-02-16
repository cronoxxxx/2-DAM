package com.example.playersapp_adriansaavedra.data.remote.datasource

import com.example.playersapp_adriansaavedra.data.remote.model.Player
import com.example.playersapp_adriansaavedra.data.remote.services.PlayerService
import javax.inject.Inject

class PlayersRemoteDataSource @Inject constructor(
    private val playerService: PlayerService
) : BaseApiResponse() {
    suspend fun getPlayers() = safeApiCall { playerService.getPlayers() }
    suspend fun getPlayer(id: String) = safeApiCall { playerService.getPlayer(id) }
    suspend fun addPlayer(player: Player) = safeApiCall { playerService.addPlayer(player) }
    suspend fun updatePlayer(id: String, player: Player) = safeApiCall { playerService.updatePlayer(id, player) }
    suspend fun deletePlayer(id: String) = safeApiCall { playerService.deletePlayer(id) }
}