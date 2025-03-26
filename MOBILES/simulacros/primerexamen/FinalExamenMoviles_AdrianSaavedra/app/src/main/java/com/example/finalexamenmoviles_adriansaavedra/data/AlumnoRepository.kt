package com.example.finalexamenmoviles_adriansaavedra.data

import com.example.finalexamenmoviles_adriansaavedra.data.remote.NetworkResult
import com.example.finalexamenmoviles_adriansaavedra.data.remote.datasource.AlumnoRemoteDataSource
import com.example.finalexamenmoviles_adriansaavedra.data.remote.datasource.BaseApiResponse
import com.example.finalexamenmoviles_adriansaavedra.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AlumnoRepository @Inject constructor(
    private val alumnoRemoteDataSource: AlumnoRemoteDataSource,
    @IoDispatcher private val dispatcher: CoroutineDispatcher

) : BaseApiResponse() {
    suspend fun getPlayers() = withContext(dispatcher) {
        try {
            alumnoRemoteDataSource.getAlumnos()
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: e.toString())
        }
    }


}