package com.example.consolesapp_adriansaavedra.domain.usecases.console

import com.example.consolesapp_adriansaavedra.data.PlayerConsoleRepository
import com.example.consolesapp_adriansaavedra.domain.model.Console
import javax.inject.Inject

class UpdateConsoleUseCase @Inject constructor(private val playerConsoleRepository: PlayerConsoleRepository) {
    suspend operator fun invoke(console: Console) = playerConsoleRepository.updateConsole(console)
}