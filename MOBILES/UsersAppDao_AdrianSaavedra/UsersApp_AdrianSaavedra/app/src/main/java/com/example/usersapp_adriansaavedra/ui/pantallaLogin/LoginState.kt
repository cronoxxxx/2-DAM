package com.example.usersapp_adriansaavedra.ui.pantallaLogin

import com.example.usersapp_adriansaavedra.ui.common.UiEvent

data class LoginState(
    val aviso: UiEvent? = null,
    val idLogin: Int = -1,
    val isLoading: Boolean = false
)