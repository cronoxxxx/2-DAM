package com.example.usersapp_adriansaavedra.data

import com.example.usersapp_adriansaavedra.data.remote.NetworkResult
import com.example.usersapp_adriansaavedra.data.remote.datasource.TasksRemoteDataSource
import com.example.usersapp_adriansaavedra.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class TaskRepository @Inject constructor(
    private val taskRemoteDataSource: TasksRemoteDataSource,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    suspend fun fetchTasks(userId: Int) = flow {
        emit(NetworkResult.Loading())
        val remoteResult = taskRemoteDataSource.fetchTasks(userId)
        emit(remoteResult)
    }.catch { e -> emit(NetworkResult.Error(e.message ?: e.toString())) }
        .flowOn(dispatcher)
}