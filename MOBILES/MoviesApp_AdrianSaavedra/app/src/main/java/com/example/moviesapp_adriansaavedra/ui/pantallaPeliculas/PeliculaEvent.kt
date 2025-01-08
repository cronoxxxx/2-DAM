package com.example.moviesapp_adriansaavedra.ui.pantallaPeliculas

sealed class PeliculaEvent {
    data class GetMovie(val id: Int) : PeliculaEvent()
   data object AvisoVisto : PeliculaEvent()
}