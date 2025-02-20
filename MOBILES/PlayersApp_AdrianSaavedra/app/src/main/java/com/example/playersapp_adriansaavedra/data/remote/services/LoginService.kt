package com.example.playersapp_adriansaavedra.data.remote.services

import com.example.playersapp_adriansaavedra.data.remote.model.Login
import com.example.playersapp_adriansaavedra.data.remote.model.Register
import com.example.playersapp_adriansaavedra.data.remote.utils.AuthenticationResponse
import com.example.playersapp_adriansaavedra.data.remote.utils.RefreshTokenRequest
import com.example.playersapp_adriansaavedra.ui.Constantes
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST(Constantes.LOGIN)
    suspend fun login(@Body login: Login): Response<AuthenticationResponse>

    @POST(Constantes.REGISTER)
    suspend fun register(@Body register: Register): Response<Unit>

    @POST(Constantes.REFRESH)
    suspend fun refreshToken(@Body request: RefreshTokenRequest): Response<AuthenticationResponse>
}