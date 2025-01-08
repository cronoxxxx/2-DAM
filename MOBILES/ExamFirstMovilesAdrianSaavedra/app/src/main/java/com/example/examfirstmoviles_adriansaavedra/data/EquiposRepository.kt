package com.example.examfirstmoviles_adriansaavedra.data

import com.example.examfirstmoviles_adriansaavedra.data.remote.datasource.EquiposRemoteDataSource
import com.example.examfirstmoviles_adriansaavedra.data.remote.services.EquiposService
import javax.inject.Inject

class EquiposRepository @Inject constructor(
    private val equiposRemoteDataSource: EquiposRemoteDataSource
) {
    suspend fun fetchEquipos() = equiposRemoteDataSource.fetchEquipos()
}