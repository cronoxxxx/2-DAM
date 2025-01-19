package com.example.examfirstmoviles_adriansaavedra.data.remote.services

import com.example.examfirstmoviles_adriansaavedra.domain.modelo.Jugador
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PlayersService {
    @GET("/equipos/{id}/jugadores")
    suspend fun getPlayersByGroup(@Path("id") id : Int) : Response<List<Jugador>>
}