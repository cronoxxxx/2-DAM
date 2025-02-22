package com.example.finalexamenmoviles_adriansaavedra.domain.usecases

import com.example.finalexamenmoviles_adriansaavedra.data.RatonRepository
import javax.inject.Inject

class GetRatones @Inject constructor(private val ratonRepository: RatonRepository) {
    suspend operator fun invoke() =
        ratonRepository.getRat()
}