package com.example.playersapp_adriansaavedra.data

import com.example.playersapp_adriansaavedra.data.remote.NetworkResult
import com.example.playersapp_adriansaavedra.data.remote.datasource.FavoritePlayersRemoteDataSource
import com.example.playersapp_adriansaavedra.data.remote.utils.PlayerNameRequest
import com.example.playersapp_adriansaavedra.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FavoritePlayerRepository @Inject constructor(
    private val favoritePlayersRemoteDataSource: FavoritePlayersRemoteDataSource,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    suspend fun getFavoritePlayers(credentialId: Int) = withContext(dispatcher) {
        try {
            favoritePlayersRemoteDataSource.getFavoritePlayers(credentialId)
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: e.toString())
        }
    }

    suspend fun getFavoritePlayer(credentialId: Int, playerId: Int) = withContext(dispatcher) {
        try {
            favoritePlayersRemoteDataSource.getFavoritePlayer(credentialId, playerId)
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: e.toString())
        }
    }

    suspend fun addFavoritePlayer(credentialId: Int, playerName: PlayerNameRequest) = withContext(dispatcher) {
        try {
            favoritePlayersRemoteDataSource.addFavoritePlayer(credentialId, playerName)
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: e.toString())
        }
    }

    suspend fun deleteFavoritePlayer(credentialId: Int, playerId: Int) = withContext(dispatcher) {
        try {
            favoritePlayersRemoteDataSource.deleteFavoritePlayer(credentialId, playerId)
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: e.toString())
        }
    }
}