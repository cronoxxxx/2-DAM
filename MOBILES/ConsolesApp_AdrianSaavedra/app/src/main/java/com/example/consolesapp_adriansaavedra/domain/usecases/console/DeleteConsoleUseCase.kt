package com.example.consolesapp_adriansaavedra.domain.usecases.console

import com.example.consolesapp_adriansaavedra.data.PlayerConsoleRepository
import javax.inject.Inject

class DeleteConsoleUseCase @Inject constructor(private val playerConsoleRepository: PlayerConsoleRepository) {
    suspend operator fun invoke(id: Int) = playerConsoleRepository.deleteConsole(id)
}