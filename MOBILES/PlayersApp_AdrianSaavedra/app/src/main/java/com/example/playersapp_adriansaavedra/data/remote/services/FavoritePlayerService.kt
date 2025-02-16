package com.example.playersapp_adriansaavedra.data.remote.services

import com.example.playersapp_adriansaavedra.data.remote.utils.PlayerNameRequest
import com.example.playersapp_adriansaavedra.domain.model.Player
import retrofit2.Response
import retrofit2.http.*

interface FavoritePlayerService {
    @GET("/favorites/{credentialId}")
    suspend fun getFavoritePlayers(@Path("credentialId") credentialId: Int): Response<List<Player>>
    @GET("/favorites/{credentialId}/{playerId}")
    suspend fun getFavoritePlayer(@Path("credentialId") credentialId: Int, @Path("playerId") playerId: Int): Response<Player>
    @POST("/favorites/{credentialId}")
    suspend fun addFavoritePlayer(@Path("credentialId") credentialId: Int, @Body playerNameRequest: PlayerNameRequest): Response<Void>

    @DELETE("/favorites/{credentialId}/{playerId}")
    suspend fun deleteFavoritePlayer(@Path("credentialId") credentialId: Int, @Path("playerId") playerId: Int): Response<Void>
}
