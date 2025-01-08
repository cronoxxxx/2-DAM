package com.example.moviesapp_adriansaavedra.data.remote.modelo

import com.example.moviesapp_adriansaavedra.domain.modelo.Movie
import com.google.gson.annotations.SerializedName

data class MovieRemote(
    val id: Int,
    val title: String,
    @SerializedName("original_title")
    val originalTitle: String,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("backdrop_path")
    val backdropPath: String,
    val overview: String,


    )

fun MovieRemote.toMovie(): Movie =
    Movie(
        id = id,
        title = title,
        originalTitle = originalTitle,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        imgPath = backdropPath,
        overview = overview
    )