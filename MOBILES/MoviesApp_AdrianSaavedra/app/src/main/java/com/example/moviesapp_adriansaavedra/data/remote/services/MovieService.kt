package com.example.moviesapp_adriansaavedra.data.remote.services

import com.example.moviesapp_adriansaavedra.data.remote.modelo.MovieRemote
import com.example.moviesapp_adriansaavedra.data.remote.modelo.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {
    @GET("/3/discover/movie")
    suspend fun getMovies(@Query("api_key") apiKey: String): Response<MovieResponse>

    @GET("/3/movie/{movie_id}")
    suspend fun getMovie(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String
    ): Response<MovieRemote>
}