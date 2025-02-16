package com.example.playersapp_adriansaavedra.data.remote.services

import com.example.playersapp_adriansaavedra.data.remote.model.*
import com.example.playersapp_adriansaavedra.data.remote.utils.*
import retrofit2.Response
import retrofit2.http.*

interface LoginService {
    @POST("/login")
    suspend fun login(@Body login: Login): Response<AuthenticationResponse>

    @POST("/register")
    suspend fun register(@Body register: Register): Response<String>

    @POST("/refresh")
    suspend fun refreshToken(@Body request: RefreshTokenRequest): Response<AuthenticationResponse>
}