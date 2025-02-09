package com.example.consolesapp_adriansaavedra.ui.pantallaAgregarConsola

import com.example.consolesapp_adriansaavedra.domain.model.Console
import com.example.consolesapp_adriansaavedra.ui.common.UiEvent

data class AddConsoleState(
    val userId: Int = 0,
    val console: Console = Console(),
    val isLoading: Boolean = false,
    val aviso: UiEvent? = null
)