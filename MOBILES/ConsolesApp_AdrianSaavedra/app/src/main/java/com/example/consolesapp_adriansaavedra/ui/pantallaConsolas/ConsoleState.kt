package com.example.consolesapp_adriansaavedra.ui.pantallaConsolas

import com.example.consolesapp_adriansaavedra.domain.model.Console
import com.example.consolesapp_adriansaavedra.ui.common.UiEvent

data class ConsoleState(
    val playerConsoles: List<Console> = emptyList(),
    val isLoading: Boolean = false,
    val aviso: UiEvent? = null,
    val selectedConsoleId: Int = 0
)