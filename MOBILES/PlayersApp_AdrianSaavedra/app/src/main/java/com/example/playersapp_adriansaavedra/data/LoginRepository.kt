package com.example.playersapp_adriansaavedra.data

import com.example.playersapp_adriansaavedra.data.remote.NetworkResult
import com.example.playersapp_adriansaavedra.data.remote.datasource.LoginRemoteDataSource
import com.example.playersapp_adriansaavedra.data.remote.model.Login
import com.example.playersapp_adriansaavedra.data.remote.model.Register
import com.example.playersapp_adriansaavedra.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val loginRemoteDataSource: LoginRemoteDataSource,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    suspend fun login(login: Login) = withContext(dispatcher) {
        try {
            loginRemoteDataSource.login(login)
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: e.toString())
        }
    }

    suspend fun register(register: Register) = withContext(dispatcher) {
        try {
            loginRemoteDataSource.register(register)
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: e.toString())
        }
    }


}

