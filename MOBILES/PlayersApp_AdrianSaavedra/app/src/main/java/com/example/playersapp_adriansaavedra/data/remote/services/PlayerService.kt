package com.example.playersapp_adriansaavedra.data.remote.services

import com.example.playersapp_adriansaavedra.data.remote.model.Player
import retrofit2.Response
import retrofit2.http.*

interface PlayerService {
    @GET("/players")
    suspend fun getPlayers(): Response<List<Player>>

    @GET("/players/{id}")
    suspend fun getPlayer(@Path("id") id: String): Response<Player>

    @POST("/players")
    suspend fun addPlayer(@Body player: Player): Response<Player>

    @PUT("/players/{id}")
    suspend fun updatePlayer(@Path("id") id: String, @Body player: Player): Response<Player>

    @DELETE("/players/{id}")
    suspend fun deletePlayer(@Path("id") id: String): Response<Void>
}