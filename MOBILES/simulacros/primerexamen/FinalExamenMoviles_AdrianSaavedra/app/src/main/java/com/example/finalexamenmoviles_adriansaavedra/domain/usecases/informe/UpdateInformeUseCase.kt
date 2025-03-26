package com.example.finalexamenmoviles_adriansaavedra.domain.usecases.informe

import com.example.finalexamenmoviles_adriansaavedra.data.InformeRepository
import com.example.finalexamenmoviles_adriansaavedra.domain.model.Informe
import javax.inject.Inject

class UpdateInformeUseCase @Inject constructor(private val informeRepository: InformeRepository) {
    suspend operator fun invoke(informe: Informe) = informeRepository.updateInforme(informe)
}

