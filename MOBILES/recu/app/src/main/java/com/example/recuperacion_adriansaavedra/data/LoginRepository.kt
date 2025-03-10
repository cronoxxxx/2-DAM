package com.example.recuperacion_adriansaavedra.data

import com.example.finalexamenmoviles_adriansaavedra.di.IoDispatcher
import com.example.recuperacion_adriansaavedra.data.remote.NetworkResult
import com.example.recuperacion_adriansaavedra.data.remote.datasource.BaseApiResponse
import com.example.recuperacion_adriansaavedra.data.remote.datasource.LoginRemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val loginRemoteDataSource: LoginRemoteDataSource,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : BaseApiResponse() {
    suspend fun login(username: String, password: String) = withContext(dispatcher) {
        try {
            loginRemoteDataSource.login(username, password)
        } catch (e: Exception) {
            NetworkResult.Error("Error al iniciar sesión: ${e.message}")
        }

    }
}