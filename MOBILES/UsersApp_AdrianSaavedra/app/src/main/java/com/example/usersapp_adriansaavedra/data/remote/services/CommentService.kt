package com.example.usersapp_adriansaavedra.data.remote.services

import com.example.usersapp_adriansaavedra.domain.modelo.Comment
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

fun interface CommentService {
    @GET("comments")
    suspend fun getComments(@Query("postId") postId: Int): Response<List<Comment>>
}