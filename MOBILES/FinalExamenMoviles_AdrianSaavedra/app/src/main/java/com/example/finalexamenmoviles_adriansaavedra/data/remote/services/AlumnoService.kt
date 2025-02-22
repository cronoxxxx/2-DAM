package com.example.finalexamenmoviles_adriansaavedra.data.remote.services

import com.example.finalexamenmoviles_adriansaavedra.data.remote.model.Alumno
import retrofit2.Response
import retrofit2.http.GET

fun interface AlumnoService {
    @GET("/alumnos")
    suspend fun getAlumnos() : Response<List<Alumno>>
}