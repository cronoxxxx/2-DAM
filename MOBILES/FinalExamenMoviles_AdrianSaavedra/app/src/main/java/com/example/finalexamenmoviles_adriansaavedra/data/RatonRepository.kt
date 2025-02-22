package com.example.finalexamenmoviles_adriansaavedra.data

import com.example.finalexamenmoviles_adriansaavedra.data.remote.NetworkResult
import com.example.finalexamenmoviles_adriansaavedra.data.remote.datasource.BaseApiResponse
import com.example.finalexamenmoviles_adriansaavedra.data.remote.model.Raton
import com.example.finalexamenmoviles_adriansaavedra.data.remote.services.RatonService
import com.example.finalexamenmoviles_adriansaavedra.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RatonRepository @Inject constructor(
    private val ratonService: RatonService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher

) : BaseApiResponse() {
    suspend fun getRat() = withContext(dispatcher) {
        try {
            safeApiCall { ratonService.getRat() }
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: e.toString())
        }


    }

    suspend fun addRat(raton:Raton) = withContext(dispatcher){
        try {
            safeApiCall { ratonService.addRat(raton) }
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: e.toString())
        }
    }
}