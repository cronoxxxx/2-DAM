package com.example.usersapp_adriansaavedra.ui.pantallaTareas

import com.example.usersapp_adriansaavedra.domain.modelo.Task

data class TasksState(
    val tasks: List<Task> = emptyList(),
    val aviso : String? = null
)