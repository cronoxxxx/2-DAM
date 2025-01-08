package com.example.examfirstmoviles_adriansaavedra.ui.pantallaPrincipal

sealed class MomentosEvent {
    data object GetEquipos : MomentosEvent()
    data object AvisoVisto : MomentosEvent()
}