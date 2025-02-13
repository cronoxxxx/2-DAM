package com.example.usersapp_adriansaavedra.ui.pantallaTareas


sealed class TasksEvent {
    data object GetTasks : TasksEvent()
    data object AvisoVisto : TasksEvent()
}