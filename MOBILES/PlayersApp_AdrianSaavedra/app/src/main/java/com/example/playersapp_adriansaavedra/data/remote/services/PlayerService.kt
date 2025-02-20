package com.example.playersapp_adriansaavedra.data.remote.services

import com.example.playersapp_adriansaavedra.domain.model.Player
import com.example.playersapp_adriansaavedra.ui.Constantes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PlayerService {
    @GET(Constantes.PLAYERS)
    suspend fun getPlayers(): Response<List<Player>>

    @GET(Constantes.PLAYER_BY_ID)
    suspend fun getPlayer(@Path(Constantes.ID) id: String): Response<Player>
}