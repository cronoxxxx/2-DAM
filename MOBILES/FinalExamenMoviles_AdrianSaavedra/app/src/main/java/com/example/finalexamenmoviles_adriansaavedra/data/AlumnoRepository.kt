package com.example.finalexamenmoviles_adriansaavedra.data

import com.example.finalexamenmoviles_adriansaavedra.data.remote.datasource.BaseApiResponse
import com.example.finalexamenmoviles_adriansaavedra.data.remote.services.AlumnoService
import com.example.finalexamenmoviles_adriansaavedra.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AlumnoRepository @Inject constructor(
    private val alumnoService: AlumnoService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher

) : BaseApiResponse() {
    suspend fun getPlayers() = withContext(dispatcher) {
        safeApiCall { alumnoService.getAlumnos() }

    }
}