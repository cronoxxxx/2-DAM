package com.example.usersapp_adriansaavedra.ui.pantallaPrincipal

sealed class MainEvent {
    data object GetUsers : MainEvent()
    data object AvisoVisto : MainEvent()
    data class GetUsersFiltrados(val filtro: String) : MainEvent()


}