package com.example.usersapp_adriansaavedra.data

import com.example.usersapp_adriansaavedra.data.remote.NetworkResult
import com.example.usersapp_adriansaavedra.data.remote.datasource.CommentsRemoteDataSource
import com.example.usersapp_adriansaavedra.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CommentRepository @Inject constructor(
    private val commentRemoteDataSource: CommentsRemoteDataSource,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    suspend fun fetchCommentsHttp(postId: Int) = flow {
        emit(NetworkResult.Loading())
        val remoteResult = commentRemoteDataSource.fetchComments(postId)
        emit(remoteResult)
    }.catch { e -> emit(NetworkResult.Error(e.message ?: e.toString())) }
        .flowOn(dispatcher)
}