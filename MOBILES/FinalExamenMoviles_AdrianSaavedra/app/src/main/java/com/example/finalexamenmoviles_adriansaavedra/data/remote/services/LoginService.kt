package com.example.finalexamenmoviles_adriansaavedra.data.remote.services

import com.example.finalexamenmoviles_adriansaavedra.data.remote.model.Token
import com.example.playersapp_adriansaavedra.data.remote.model.LoginUser
import com.example.finalexamenmoviles_adriansaavedra.data.remote.utils.AuthenticationResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("/login")
    suspend fun login(@Body loginUser: LoginUser): Response<Token>
}