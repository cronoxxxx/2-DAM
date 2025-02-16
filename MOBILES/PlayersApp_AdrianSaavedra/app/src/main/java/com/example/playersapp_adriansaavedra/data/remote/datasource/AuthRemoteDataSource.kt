package com.example.playersapp_adriansaavedra.data.remote.datasource

import com.example.playersapp_adriansaavedra.data.remote.model.Auth
import com.example.playersapp_adriansaavedra.data.remote.model.Register
import com.example.playersapp_adriansaavedra.data.remote.services.AuthService
import javax.inject.Inject

class AuthRemoteDataSource @Inject constructor(
    private val authService: AuthService
) : BaseApiResponse() {
    suspend fun login(auth: Auth) = safeApiCall { authService.login(auth) }
    suspend fun register(register: Register) = safeApiCall { authService.register(register) }
}