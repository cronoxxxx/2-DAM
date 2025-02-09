package com.example.usersapp_adriansaavedra.data.remote.services

import com.example.usersapp_adriansaavedra.data.remote.modelo.UserRemote
import retrofit2.Response
import retrofit2.http.*
interface UserService {
    @GET("users")
    suspend fun getUsers(): Response<List<UserRemote>>

    @GET("users/{id}")
    suspend fun getUser(@Path("id") id: Int): Response<UserRemote>

    @POST("users")
    suspend fun addUser(@Body user: UserRemote): Response<UserRemote>

    @PUT("users/{id}")
    suspend fun updateUser(@Path("id") id: Int, @Body user: UserRemote): Response<UserRemote>

    @DELETE("users/{id}")
    suspend fun deleteUser(@Path("id") id: Int): Response<Unit>
}