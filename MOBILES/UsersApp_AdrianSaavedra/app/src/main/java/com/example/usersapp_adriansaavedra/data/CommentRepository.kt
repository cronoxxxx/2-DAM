package com.example.usersapp_adriansaavedra.data

import com.example.usersapp_adriansaavedra.data.remote.datasource.CommentsRemoteDataSource
import javax.inject.Inject

class CommentRepository @Inject constructor(
    private val commentRemoteDataSource: CommentsRemoteDataSource
) {
    suspend fun fetchComments(postId: Int) = commentRemoteDataSource.fetchComments(postId)
}