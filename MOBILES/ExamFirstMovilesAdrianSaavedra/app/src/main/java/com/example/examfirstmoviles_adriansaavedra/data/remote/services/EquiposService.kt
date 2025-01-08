package com.example.examfirstmoviles_adriansaavedra.data.remote.services

import com.example.examfirstmoviles_adriansaavedra.domain.modelo.Equipo
import retrofit2.Response
import retrofit2.http.GET

fun interface EquiposService {
    @GET("/equipos")
    suspend fun getEquipos(): Response<List<Equipo>>
}