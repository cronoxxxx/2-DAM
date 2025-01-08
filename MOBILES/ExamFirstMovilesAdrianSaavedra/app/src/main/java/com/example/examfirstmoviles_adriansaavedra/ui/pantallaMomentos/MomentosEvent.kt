package com.example.examfirstmoviles_adriansaavedra.ui.pantallaMomentos

sealed class MainEvent {
    data object GetEquipos : MainEvent()
    data object AvisoVisto : MainEvent()
}