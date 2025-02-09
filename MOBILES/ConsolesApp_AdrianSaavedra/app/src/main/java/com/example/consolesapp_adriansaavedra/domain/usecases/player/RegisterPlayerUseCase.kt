package com.example.consolesapp_adriansaavedra.domain.usecases.player

import com.example.consolesapp_adriansaavedra.data.NetworkResult
import com.example.consolesapp_adriansaavedra.data.PlayerConsoleRepository
import com.example.consolesapp_adriansaavedra.data.local.model.validate
import com.example.consolesapp_adriansaavedra.domain.model.Player
import com.example.consolesapp_adriansaavedra.ui.Constantes
import javax.inject.Inject

class RegisterPlayerUseCase @Inject constructor(private val playerConsoleRepository: PlayerConsoleRepository) {
    suspend operator fun invoke(player: Player) =
        if (player.validate()) {
            playerConsoleRepository.insertPlayer(player)
        } else {
            NetworkResult.Error(Constantes.EMPTY_FIELDS)
        }
}