package com.example.moviesapp_adriansaavedra.domain.modelo

data class Movie(
    val id: Int = 0,
    val title: String = "",
    val originalTitle : String = "",
    val releaseDate: String = "",
    val voteAverage: Double = 0.0,
    val imgPath: String = "",
    val overview: String = "",
)