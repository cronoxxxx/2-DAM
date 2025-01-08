package com.example.moviesapp_adriansaavedra.domain.usecases.peliculas

import com.example.moviesapp_adriansaavedra.data.MovieRepository
import javax.inject.Inject

class GetMovieUseCase @Inject constructor(private val movieRepository: MovieRepository) {
    suspend operator fun invoke(id: Int) = movieRepository.fetchMovie(id)
}