package com.example.finalexamenmoviles_adriansaavedra.domain.usecases

import com.example.finalexamenmoviles_adriansaavedra.data.AlumnoRepository
import javax.inject.Inject

class GetAlumnos @Inject constructor(private val alumnoRepository: AlumnoRepository) {
    suspend operator fun invoke() =
        alumnoRepository.getPlayers()
}