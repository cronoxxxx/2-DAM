package com.example.moviesapp_adriansaavedra.data

import com.example.moviesapp_adriansaavedra.data.remote.datasource.MovieRemoteDataSource
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieRemoteDataSource: MovieRemoteDataSource,
) {
    suspend fun fetchMovies() = movieRemoteDataSource.fetchMovies()
    suspend fun fetchMovie(id: Int) = movieRemoteDataSource.fetchMovie(id)
}