package com.example.usersapp_adriansaavedra.ui.pantallaTareas


sealed class TasksEvent {
    data class GetTasks(val userId: Int) : TasksEvent()
    data object AvisoVisto : TasksEvent()
}