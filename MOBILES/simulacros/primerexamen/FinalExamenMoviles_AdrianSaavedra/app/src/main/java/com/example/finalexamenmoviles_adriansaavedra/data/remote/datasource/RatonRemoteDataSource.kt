package com.example.finalexamenmoviles_adriansaavedra.data.remote.datasource

import com.example.finalexamenmoviles_adriansaavedra.data.remote.services.RatonService
import com.example.finalexamenmoviles_adriansaavedra.domain.model.Raton
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RatonRemoteDataSource @Inject constructor(private val ratonService: RatonService): BaseApiResponse(){
    suspend fun addRaton(raton: Raton) = safeApiCall { ratonService.addRat(raton) }
    suspend fun getRatones() = safeApiCall { ratonService.getRat() }
}