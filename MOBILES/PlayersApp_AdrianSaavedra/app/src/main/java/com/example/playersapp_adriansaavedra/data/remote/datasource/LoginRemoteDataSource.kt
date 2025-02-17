package com.example.playersapp_adriansaavedra.data.remote.datasource

import com.example.playersapp_adriansaavedra.data.remote.model.Login
import com.example.playersapp_adriansaavedra.data.remote.model.Register
import com.example.playersapp_adriansaavedra.data.remote.services.LoginService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRemoteDataSource @Inject constructor(
    private val loginService: LoginService,
) : BaseApiResponse() {
    suspend fun login(login: Login) = safeApiCall { loginService.login(login) }
    suspend fun register(register: Register) = safeApiCall { loginService.register(register) }
}