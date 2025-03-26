package com.example.finalexamenmoviles_adriansaavedra.data

import com.example.finalexamenmoviles_adriansaavedra.data.remote.NetworkResult
import com.example.finalexamenmoviles_adriansaavedra.data.remote.datasource.BaseApiResponse
import com.example.finalexamenmoviles_adriansaavedra.data.remote.datasource.RatonRemoteDataSource
import com.example.finalexamenmoviles_adriansaavedra.di.IoDispatcher
import com.example.finalexamenmoviles_adriansaavedra.domain.model.Raton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RatonRepository @Inject constructor(
    private val ratonRemoteDataSource: RatonRemoteDataSource,
    @IoDispatcher private val dispatcher: CoroutineDispatcher

) : BaseApiResponse() {
    suspend fun getRats() = withContext(dispatcher) {
        try {
            ratonRemoteDataSource.getRatones()
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: e.toString())
        }
    }

    suspend fun addRat(raton: Raton) = withContext(dispatcher) {
        try {
            ratonRemoteDataSource.addRaton(raton)
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: e.toString())
        }
    }
}