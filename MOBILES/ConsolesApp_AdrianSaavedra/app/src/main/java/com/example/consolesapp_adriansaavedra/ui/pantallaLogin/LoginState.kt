package com.example.consolesapp_adriansaavedra.ui.pantallaLogin

import com.example.consolesapp_adriansaavedra.ui.common.UiEvent

data class LoginState(
    val aviso: UiEvent? = null,
    val idLogin: String = "",
    val username: String = "",
    val password: String = "",

    val isLoading: Boolean = false
)