package com.example.playersapp_adriansaavedra.data.remote.services

import com.example.playersapp_adriansaavedra.domain.model.Player
import retrofit2.Response
import retrofit2.http.*

interface PlayerService {
    @GET("/players")
    suspend fun getPlayers(): Response<List<Player>>

    @GET("/players/{id}")
    suspend fun getPlayer(@Path("id") id: String): Response<Player>
}