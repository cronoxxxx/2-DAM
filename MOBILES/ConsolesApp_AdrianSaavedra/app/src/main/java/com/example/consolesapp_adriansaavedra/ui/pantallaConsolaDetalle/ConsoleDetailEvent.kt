package com.example.consolesapp_adriansaavedra.ui.pantallaConsolaDetalle

import com.example.consolesapp_adriansaavedra.domain.model.Console

sealed class ConsoleDetailEvent {
    data class GetConsole(val consoleId: Int) : ConsoleDetailEvent()
    data class UpdateConsole(val console: Console) : ConsoleDetailEvent()
    data class DeleteConsole(val consoleId: Int) : ConsoleDetailEvent()
    data object AvisoVisto : ConsoleDetailEvent()
    data class OnNameChange(val value: String) : ConsoleDetailEvent()
    data class OnModelChange(val value: String) : ConsoleDetailEvent()
    data class OnPriceChange(val value: String) : ConsoleDetailEvent()
}