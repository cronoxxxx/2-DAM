package com.example.examfirstmoviles_adriansaavedra.data.remote.services

import com.example.examfirstmoviles_adriansaavedra.domain.modelo.Momento
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

fun interface MomentosService {
    @GET("/equipos/{id}/momentos")
    suspend fun getMomentsByGroup(@Path("id") id : Int) : Response<List<Momento>>
}