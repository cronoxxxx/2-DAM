package com.example.finalexamenmoviles_adriansaavedra.data.remote.services

import com.example.finalexamenmoviles_adriansaavedra.data.remote.utils.Token
import com.example.finalexamenmoviles_adriansaavedra.data.remote.utils.LoginUser
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

fun interface LoginService {
    @POST("/login")
    suspend fun login(@Body loginUser: LoginUser): Response<Token>
}