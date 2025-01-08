package com.example.examfirstmoviles_adriansaavedra.data.remote.datasource

import com.example.examfirstmoviles_adriansaavedra.data.remote.services.PlayersService
import javax.inject.Inject

class PlayersRemoteDataSource @Inject constructor(private val playersService: PlayersService) :
    BaseApiResponse(){
    suspend fun fetchPlayers (id : Int) = safeApiCall { playersService.getPlayersByGroup(id) }
}