package com.example.finalexamenmoviles_adriansaavedra.ui.pantallaLogin

import com.example.finalexamenmoviles_adriansaavedra.ui.common.UiEvent

data class LoginState(
    val aviso: UiEvent? = null,
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false
)