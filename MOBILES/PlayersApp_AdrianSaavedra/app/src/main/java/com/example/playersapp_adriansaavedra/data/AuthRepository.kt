package com.example.playersapp_adriansaavedra.data

import com.example.playersapp_adriansaavedra.data.remote.datasource.AuthRemoteDataSource
import com.example.playersapp_adriansaavedra.data.remote.model.Auth
import com.example.playersapp_adriansaavedra.data.remote.model.Register
import com.example.playersapp_adriansaavedra.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    suspend fun login(auth: Auth) = withContext(dispatcher) {
        authRemoteDataSource.login(auth)
    }

    suspend fun register(register: Register) = withContext(dispatcher) {
        authRemoteDataSource.register(register)
    }

}

