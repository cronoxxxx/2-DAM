package com.example.moviesapp_adriansaavedra.ui.pantallaPeliculas

import com.example.moviesapp_adriansaavedra.domain.modelo.Movie
import com.example.moviesapp_adriansaavedra.ui.common.UiEvent

data class PeliculaState(
    val movie: Movie = Movie(),
    val aviso: UiEvent? = null
)