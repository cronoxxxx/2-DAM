package com.example.examfirstmoviles_adriansaavedra.data.remote.datasource

import com.example.examfirstmoviles_adriansaavedra.data.remote.services.EquiposService
import javax.inject.Inject

class EquiposRemoteDataSource @Inject constructor(private val equiposService: EquiposService) :
    BaseApiResponse(){
        suspend fun fetchEquipos () =  safeApiCall { equiposService.getEquipos() }
    }