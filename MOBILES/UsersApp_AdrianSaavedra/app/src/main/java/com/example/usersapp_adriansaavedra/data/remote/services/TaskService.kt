package com.example.usersapp_adriansaavedra.data.remote.services

import com.example.usersapp_adriansaavedra.domain.modelo.Task
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

fun interface TaskService {
    @GET("todos")
    suspend fun getTasks(@Query("userId") userId: Int): Response<List<Task>>
}