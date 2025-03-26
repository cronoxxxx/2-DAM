package com.example.finalexamenmoviles_adriansaavedra.domain.usecases.raton

import com.example.finalexamenmoviles_adriansaavedra.data.RatonRepository
import javax.inject.Inject

class GetRatonesUseCase @Inject constructor(private val ratonRepository: RatonRepository) {
    suspend operator fun invoke() =
        ratonRepository.getRats()
}