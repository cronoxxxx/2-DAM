package com.example.consolesapp_adriansaavedra.ui.pantallaConsolaDetalle

import com.example.consolesapp_adriansaavedra.domain.model.Console
import com.example.consolesapp_adriansaavedra.ui.common.UiEvent

data class ConsoleDetailState (
    val isLoading: Boolean = false,
    val console: Console? = null,
    val aviso: UiEvent? = null
)