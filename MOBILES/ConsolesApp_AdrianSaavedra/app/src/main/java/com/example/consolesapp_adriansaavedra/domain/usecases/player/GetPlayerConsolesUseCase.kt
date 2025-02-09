package com.example.consolesapp_adriansaavedra.domain.usecases.player

import com.example.consolesapp_adriansaavedra.data.PlayerConsoleRepository
import javax.inject.Inject

class GetPlayerConsolesUseCase @Inject constructor(private val playerConsoleRepository: PlayerConsoleRepository) {
    suspend operator fun invoke(id: Int) = playerConsoleRepository.fetchPlayerWithConsoles(id)
}