package com.example.moviesapp_adriansaavedra.ui.pantallaMain

import com.example.moviesapp_adriansaavedra.domain.modelo.Movie
import com.example.moviesapp_adriansaavedra.ui.common.UiEvent

data class MainState (
    val movies: List<Movie>? = null,
    val aviso : UiEvent? = null,
)