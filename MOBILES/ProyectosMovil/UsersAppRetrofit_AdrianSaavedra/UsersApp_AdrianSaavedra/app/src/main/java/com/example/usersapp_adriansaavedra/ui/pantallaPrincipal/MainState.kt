package com.example.usersapp_adriansaavedra.ui.pantallaPrincipal

import com.example.usersapp_adriansaavedra.domain.modelo.User

data class MainState(
    val users: List<User> = emptyList(),
    val aviso: String? = null,
)