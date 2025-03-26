package com.example.finalexamenmoviles_adriansaavedra.domain.usecases.informe

import com.example.finalexamenmoviles_adriansaavedra.data.InformeRepository
import javax.inject.Inject

class GetInformesUseCase @Inject constructor(private val informeRepository: InformeRepository) {
    suspend operator fun invoke() = informeRepository.getAllInformes()
}