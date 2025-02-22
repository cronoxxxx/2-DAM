package com.example.finalexamenmoviles_adriansaavedra.domain.usecases

import com.example.finalexamenmoviles_adriansaavedra.data.RatonRepository
import com.example.finalexamenmoviles_adriansaavedra.data.remote.model.Raton
import javax.inject.Inject

class AddRaton @Inject constructor(private val ratonRepository: RatonRepository) {
    suspend operator fun invoke(raton:Raton) =
        ratonRepository.addRat(raton)
}