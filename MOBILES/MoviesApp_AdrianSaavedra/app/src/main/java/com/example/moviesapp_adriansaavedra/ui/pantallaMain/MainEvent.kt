package com.example.moviesapp_adriansaavedra.ui.pantallaMain

sealed class MainEvent {
    data object GetMovies : MainEvent()
    data object AvisoVisto : MainEvent()
}