package com.example.playersapp_adriansaavedra.domain.usecases.player

import com.example.playersapp_adriansaavedra.data.PlayerRepository
import javax.inject.Inject

class GetPlayersUseCase @Inject constructor(
    private val playerRepository: PlayerRepository
) {
    suspend operator fun invoke() = playerRepository.getPlayers()

}