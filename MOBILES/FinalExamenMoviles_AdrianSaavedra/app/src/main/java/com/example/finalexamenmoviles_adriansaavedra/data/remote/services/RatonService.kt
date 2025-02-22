package com.example.finalexamenmoviles_adriansaavedra.data.remote.services

import com.example.finalexamenmoviles_adriansaavedra.data.remote.model.Alumno
import com.example.finalexamenmoviles_adriansaavedra.data.remote.model.Raton
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RatonService {
    @GET("/ratones")
    suspend fun getRat() : Response<List<Raton>>
    @POST("/ratones")
    suspend fun addRat (@Body raton: Raton) : Response<Raton>
}