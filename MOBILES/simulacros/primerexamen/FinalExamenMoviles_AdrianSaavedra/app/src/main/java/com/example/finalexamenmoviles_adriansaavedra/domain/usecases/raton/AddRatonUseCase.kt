package com.example.finalexamenmoviles_adriansaavedra.domain.usecases.raton

import com.example.finalexamenmoviles_adriansaavedra.data.RatonRepository
import com.example.finalexamenmoviles_adriansaavedra.domain.model.Raton
import javax.inject.Inject

class AddRatonUseCase @Inject constructor(private val ratonRepository: RatonRepository) {
    suspend operator fun invoke(raton: Raton) =
        ratonRepository.addRat(raton)
}