package com.example.consolesapp_adriansaavedra.domain.usecases.console

import com.example.consolesapp_adriansaavedra.data.NetworkResult
import com.example.consolesapp_adriansaavedra.data.PlayerConsoleRepository
import com.example.consolesapp_adriansaavedra.data.local.model.validate
import com.example.consolesapp_adriansaavedra.domain.model.Console
import com.example.consolesapp_adriansaavedra.ui.Constantes
import javax.inject.Inject

class AddConsoleUseCase @Inject constructor(private val playerConsoleRepository: PlayerConsoleRepository) {
    suspend operator fun invoke(userId: Int, consola: Console) =
        if (consola.validate()) {
            playerConsoleRepository.insertConsole(userId, consola)
        } else {
            NetworkResult.Error(Constantes.EMPTY_FIELDS)
        }
}

