package com.example.usersapp_adriansaavedra.ui.pantallaTareas

import com.example.usersapp_adriansaavedra.domain.modelo.Task
import com.example.usersapp_adriansaavedra.ui.common.UiEvent

data class TasksState(
    val tasks: List<Task> = emptyList(),
    val aviso: UiEvent? = null,
    val isLoading: Boolean = false
)