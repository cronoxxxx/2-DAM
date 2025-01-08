package com.example.examfirstmoviles_adriansaavedra.data

import com.example.examfirstmoviles_adriansaavedra.data.remote.datasource.PlayersRemoteDataSource
import javax.inject.Inject

class PlayersRepository  @Inject constructor(private val playersRemoteDataSource: PlayersRemoteDataSource) {
    suspend fun fetchPlayers(id:Int) = playersRemoteDataSource.fetchPlayers(id);
}