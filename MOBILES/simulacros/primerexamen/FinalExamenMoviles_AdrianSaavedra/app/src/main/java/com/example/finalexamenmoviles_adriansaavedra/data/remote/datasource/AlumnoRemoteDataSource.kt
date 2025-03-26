package com.example.finalexamenmoviles_adriansaavedra.data.remote.datasource

import com.example.finalexamenmoviles_adriansaavedra.data.remote.services.AlumnoService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlumnoRemoteDataSource @Inject constructor(private val alumnoService: AlumnoService): BaseApiResponse(){
    suspend fun getAlumnos() = safeApiCall { alumnoService.getAlumnos() }
}