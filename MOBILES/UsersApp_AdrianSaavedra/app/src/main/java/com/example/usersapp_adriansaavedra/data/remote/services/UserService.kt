package com.example.usersapp_adriansaavedra.data.remote.services

import com.example.usersapp_adriansaavedra.data.remote.modelo.UserRemote
import retrofit2.Response
import retrofit2.http.GET

fun interface UserService {
    @GET("users")
    suspend fun getUsers(): Response<List<UserRemote>>
}