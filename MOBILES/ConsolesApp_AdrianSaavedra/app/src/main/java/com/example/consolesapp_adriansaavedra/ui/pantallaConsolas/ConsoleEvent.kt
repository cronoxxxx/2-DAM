package com.example.consolesapp_adriansaavedra.ui.pantallaConsolas

sealed class ConsoleEvent { data class OnConsoleClick(val consoleId: Int) : ConsoleEvent()
    object AvisoVisto : ConsoleEvent()
}
