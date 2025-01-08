package com.example.usersapp_adriansaavedra.data.remote.datasource

import com.example.usersapp_adriansaavedra.data.remote.services.CommentService
import javax.inject.Inject

class CommentsRemoteDataSource @Inject constructor(private val commentService: CommentService) :
    BaseApiResponse() {
    suspend fun fetchComments(postId: Int) = safeApiCall { commentService.getComments(postId) }
}