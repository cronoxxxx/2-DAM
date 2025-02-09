package com.example.usersapp_adriansaavedra.ui.pantallaUsuario

import com.example.usersapp_adriansaavedra.domain.modelo.User
import com.example.usersapp_adriansaavedra.ui.common.UiEvent

data class DetalleState(
    val user: User = User(),
    val aviso: UiEvent? = null
)