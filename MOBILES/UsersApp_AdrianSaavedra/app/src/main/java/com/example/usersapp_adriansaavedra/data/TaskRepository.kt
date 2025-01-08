package com.example.usersapp_adriansaavedra.data

import com.example.usersapp_adriansaavedra.data.remote.datasource.TasksRemoteDataSource
import javax.inject.Inject

class TaskRepository @Inject constructor(
    private val taskRemoteDataSource: TasksRemoteDataSource
) {
    suspend fun fetchTasks(userId: Int) = taskRemoteDataSource.fetchTasks(userId)
}