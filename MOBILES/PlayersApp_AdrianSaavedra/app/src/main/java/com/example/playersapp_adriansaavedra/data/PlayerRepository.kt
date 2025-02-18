package com.example.playersapp_adriansaavedra.data

import com.example.playersapp_adriansaavedra.data.remote.NetworkResult
import com.example.playersapp_adriansaavedra.data.remote.datasource.PlayersRemoteDataSource
import com.example.playersapp_adriansaavedra.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PlayerRepository @Inject constructor(
    private val playerRemoteDataSource: PlayersRemoteDataSource,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    suspend fun getPlayers() = withContext(dispatcher) {
        try {
            playerRemoteDataSource.getPlayers()
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: e.toString())
        }
    }

    suspend fun getPlayer(id: String) = withContext(dispatcher) {
        try {
            playerRemoteDataSource.getPlayer(id)
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: e.toString())
        }
    }
}