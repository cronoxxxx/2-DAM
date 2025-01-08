package com.example.usersapp_adriansaavedra.data.remote.datasource

import com.example.usersapp_adriansaavedra.data.remote.services.TaskService
import javax.inject.Inject

class TasksRemoteDataSource @Inject constructor(private val taskService: TaskService) :
    BaseApiResponse() {
    suspend fun fetchTasks(userId: Int) = safeApiCall { taskService.getTasks(userId) }
}