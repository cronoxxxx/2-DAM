package com.example.hospitalapp_adriansaavedra.ui.pantallaLogin

import com.example.hospitalapp_adriansaavedra.ui.common.UiEvent

data class LoginState(
    val aviso: UiEvent? = null,
    val idLogin: String = "",
    val isLoading: Boolean = false
)