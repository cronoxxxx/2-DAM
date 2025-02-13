package com.example.usersapp_adriansaavedra.domain.usecases.tareas

import com.example.usersapp_adriansaavedra.data.TaskRepository
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(private val taskRepository: TaskRepository) {
    suspend operator fun invoke() = taskRepository.fetchTasks()
}


