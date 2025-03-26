package com.example.finalexamenmoviles_adriansaavedra.ui.pantallaInformes

sealed class InformesEvent {
    data object AvisoVisto : InformesEvent()
    data object GetInformes : InformesEvent()
    data object LoadInitialInformes : InformesEvent()
    data class InformeSelected(val id: Int) : InformesEvent()
}