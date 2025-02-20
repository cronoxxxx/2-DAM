package com.example.finalexamenmoviles_adriansaavedra.data.remote.services

import com.example.playersapp_adriansaavedra.data.remote.model.Login
import com.example.finalexamenmoviles_adriansaavedra.data.remote.utils.AuthenticationResponse
import com.example.finalexamenmoviles_adriansaavedra.data.remote.utils.RefreshTokenRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("/login")
    suspend fun login(@Body login: Login): Response<AuthenticationResponse>

    @POST("/refresh")
    suspend fun refreshToken(@Body refreshToken: RefreshTokenRequest): Response<AuthenticationResponse> //cambiar x lo que haya en el examen
}