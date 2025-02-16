package com.example.playersapp_adriansaavedra.data.remote.services

import com.example.playersapp_adriansaavedra.data.remote.model.*
import com.example.playersapp_adriansaavedra.data.remote.utils.*
import retrofit2.Response
import retrofit2.http.*

interface AuthService {
    @POST("/login")
    suspend fun login(@Body auth: Auth): Response<AuthenticationResponse>

    @POST("/register")
    suspend fun register(@Body register: Register): Response<String>

    @POST("/refresh-token")
    suspend fun refreshToken(@Body request: RefreshTokenRequest): Response<AuthenticationResponse>
}