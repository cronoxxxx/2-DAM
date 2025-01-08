package com.example.apppersonas_adriansaavedra.ui.pantallaMain

sealed class MainEvent {
    data object GetPersonas : MainEvent()
    data object ErrorVisto : MainEvent()
}