package com.example.recuperacion_adriansaavedra.data.remote.services

import com.example.recuperacion_adriansaavedra.domain.model.LoginUser
import com.example.recuperacion_adriansaavedra.domain.model.Token
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("/login")
    suspend fun login(@Body loginUser: LoginUser): Response<Token>
}