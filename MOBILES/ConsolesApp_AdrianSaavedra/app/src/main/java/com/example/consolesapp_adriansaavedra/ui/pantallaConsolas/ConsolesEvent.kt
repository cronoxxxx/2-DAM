package com.example.consolesapp_adriansaavedra.ui.pantallaConsolas

sealed class ConsolesEvent {
    data class OnConsolesClick(val consoleId: Int) : ConsolesEvent()
    data object AvisoVisto : ConsolesEvent()
    data class LoadConsoles(val userId: Int) : ConsolesEvent()
}
