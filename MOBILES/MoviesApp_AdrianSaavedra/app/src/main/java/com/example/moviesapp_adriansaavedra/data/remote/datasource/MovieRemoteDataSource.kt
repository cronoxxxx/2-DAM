package com.example.moviesapp_adriansaavedra.data.remote.datasource

import com.example.moviesapp_adriansaavedra.BuildConfig
import com.example.moviesapp_adriansaavedra.data.remote.NetworkResult
import com.example.moviesapp_adriansaavedra.data.remote.modelo.toMovie
import com.example.moviesapp_adriansaavedra.data.remote.services.MovieService
import com.example.moviesapp_adriansaavedra.domain.modelo.Movie

import javax.inject.Inject

class MovieRemoteDataSource @Inject constructor(
    private val movieService: MovieService
) : BaseApiResponse() {
    suspend fun fetchMovies(): NetworkResult<List<Movie>?> =
        safeApiCall { movieService.getMovies(BuildConfig.API_KEY) }.map { response ->
            response?.results?.map { it.toMovie() } 
        }

    suspend fun fetchMovie(id: Int): NetworkResult<Movie?> =
        safeApiCall { movieService.getMovie(id, BuildConfig.API_KEY) }.map { it?.toMovie() }
}