package com.example.playersapp_adriansaavedra.data.remote.datasource

import com.example.playersapp_adriansaavedra.data.remote.model.Login
import com.example.playersapp_adriansaavedra.data.remote.model.Register
import com.example.playersapp_adriansaavedra.data.remote.services.LoginService
import com.example.playersapp_adriansaavedra.data.remote.utils.TokenRefresher
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRemoteDataSource @Inject constructor(
    private val loginService: LoginService,
    tokenRefresher: TokenRefresher
) : BaseApiResponse(tokenRefresher) {
    suspend fun login(login: Login) = safeApiCall { loginService.login(login) }
    suspend fun register(register: Register) = safeApiCall { loginService.register(register) }
}