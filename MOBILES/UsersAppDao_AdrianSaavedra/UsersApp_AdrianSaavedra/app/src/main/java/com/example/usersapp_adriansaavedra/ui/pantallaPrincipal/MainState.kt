package com.example.usersapp_adriansaavedra.ui.pantallaPrincipal

import com.example.usersapp_adriansaavedra.domain.modelo.User
import com.example.usersapp_adriansaavedra.ui.common.UiEvent

data class MainState(
    val users: List<User>? = emptyList(),
    val aviso: UiEvent? = null,
    val isLoading: Boolean = false
)