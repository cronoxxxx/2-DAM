package com.example.recuperacion_adriansaavedra.ui.pLogin

import com.example.finalexamenmoviles_adriansaavedra.ui.common.UiEvent

// Aquí defines el estado de la pantalla de login
data class LoginState(
    val aviso: UiEvent? = null,
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false
)