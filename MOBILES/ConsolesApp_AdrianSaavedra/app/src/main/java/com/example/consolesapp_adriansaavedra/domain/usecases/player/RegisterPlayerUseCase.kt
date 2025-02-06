package com.example.consolesapp_adriansaavedra.domain.usecases.player
import com.example.consolesapp_adriansaavedra.data.local.model.validate
import com.example.consolesapp_adriansaavedra.data.PlayerConsoleRepository
import com.example.consolesapp_adriansaavedra.domain.model.Player
import javax.inject.Inject

class RegisterPlayerUseCase@Inject constructor(private val playerConsoleRepository: PlayerConsoleRepository) {
    suspend operator fun invoke(player: Player) {
        if (player.validate()) return playerConsoleRepository.insertPlayer(player)
    }
}