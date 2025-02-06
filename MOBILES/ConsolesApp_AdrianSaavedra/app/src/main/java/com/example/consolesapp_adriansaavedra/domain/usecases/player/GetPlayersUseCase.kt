package com.example.consolesapp_adriansaavedra.domain.usecases.player

import com.example.consolesapp_adriansaavedra.data.PlayerConsoleRepository
import javax.inject.Inject

class GetPlayersUseCase @Inject constructor(private val playerConsoleRepository: PlayerConsoleRepository) {
suspend operator fun invoke() = playerConsoleRepository.fetchAllPlayers()
}