package com.example.examfirstmoviles_adriansaavedra.domain.usecases.equipo

import com.example.examfirstmoviles_adriansaavedra.data.EquiposRepository
import javax.inject.Inject

class GetEquiposUseCase @Inject constructor(private val equiposRepository: EquiposRepository) {
    suspend operator fun invoke() = equiposRepository.fetchEquipos()
}