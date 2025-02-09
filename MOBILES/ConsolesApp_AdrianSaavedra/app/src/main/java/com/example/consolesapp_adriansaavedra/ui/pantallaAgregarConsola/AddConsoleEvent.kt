package com.example.consolesapp_adriansaavedra.ui.pantallaAgregarConsola

import com.example.consolesapp_adriansaavedra.domain.model.Console

sealed class AddConsoleEvent {
    data class AddConsole(val userId: Int, val console: Console) : AddConsoleEvent()
    data class OnNameChange(val value: String) : AddConsoleEvent()
    data class OnModelChange(val value: String) : AddConsoleEvent()
    data class OnPriceChange(val value: String) : AddConsoleEvent()
    data class UpdateUserId(val userId: Int) : AddConsoleEvent()

    data object AvisoVisto : AddConsoleEvent()
}