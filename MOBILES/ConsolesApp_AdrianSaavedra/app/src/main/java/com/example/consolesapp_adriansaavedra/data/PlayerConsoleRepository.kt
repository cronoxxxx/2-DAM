package com.example.consolesapp_adriansaavedra.data

import com.example.consolesapp_adriansaavedra.data.local.PlayerConsoleDao
import com.example.consolesapp_adriansaavedra.data.local.model.PlayerConsoleCrossRef
import com.example.consolesapp_adriansaavedra.data.local.model.toConsole
import com.example.consolesapp_adriansaavedra.data.local.model.toEntity
import com.example.consolesapp_adriansaavedra.data.local.model.toPlayer
import com.example.consolesapp_adriansaavedra.di.IoDispatcher
import com.example.consolesapp_adriansaavedra.domain.model.Console
import com.example.consolesapp_adriansaavedra.domain.model.Player
import com.example.consolesapp_adriansaavedra.ui.Constantes
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PlayerConsoleRepository @Inject constructor(
    private val playerConsoleDao: PlayerConsoleDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    suspend fun deleteConsole(id: Int) = withContext(dispatcher) {
        try {
            playerConsoleDao.deleteConsole(id)
            NetworkResult.Success(Unit)
        } catch (e: Exception) {
            NetworkResult.Error("${Constantes.ERROR_DELETING_CONSOLE}${e.message}")
        }
    }

    suspend fun fetchAllPlayers() = withContext(dispatcher) {
        try {
            val players = playerConsoleDao.getAllPlayers().map { it.toPlayer() }
            if (players.isEmpty()) {
                NetworkResult.Error(Constantes.NO_PLAYERS_FOUND)
            } else {
                NetworkResult.Success(players)
            }
        } catch (e: Exception) {
            NetworkResult.Error("${Constantes.ERROR_FETCHING_PLAYERS}${e.message}")
        }
    }

    suspend fun fetchPlayerWithConsoles(id: Int) = withContext(dispatcher) {
        try {
            val result = playerConsoleDao.getPlayerWithConsoles(id).toPlayer()
            NetworkResult.Success(result)
        } catch (e: Exception) {
            NetworkResult.Error("${Constantes.ERROR_FETCHING_PLAYER_WITH_CONSOLES}${e.message}")
        }

    }


    suspend fun fetchConsole(id: Int) = withContext(dispatcher) {
        try {
            val result = playerConsoleDao.getConsole(id).toConsole()
            NetworkResult.Success(result)
        } catch (e: Exception) {
            NetworkResult.Error("${Constantes.ERROR_FETCHING_CONSOLE}${e.message}")
        }
    }

    suspend fun insertConsole(userId: Int, consola: Console) = withContext(dispatcher) {
        try {
            val result = playerConsoleDao.getAllConsoles()
            val existingConsole = result.find { it.nombre == consola.nombre }
            if (existingConsole == null) {
                val newConsoleId = playerConsoleDao.insertConsole(consola.toEntity())
                playerConsoleDao.insertCrossRef(PlayerConsoleCrossRef(userId, newConsoleId.toInt()))
                NetworkResult.Success(Unit)
            } else {
                NetworkResult.Error(Constantes.CONSOLE_ALREADY_EXISTS)
            }
        } catch (e: Exception) {
            NetworkResult.Error("${Constantes.ERROR_INSERTING_CONSOLE}${e.message}")
        }
    }


    suspend fun insertPlayer(player: Player): NetworkResult<Unit> = withContext(dispatcher) {
        try {
            val result = playerConsoleDao.getAllPlayers()
            val existingUser = result.find { it.username == player.username }
            if (existingUser == null) {
                playerConsoleDao.insertPlayer(player.toEntity())
                NetworkResult.Success(Unit)
            } else {
                NetworkResult.Error(Constantes.USER_ALREADY_EXISTS)
            }
        } catch (e: Exception) {
            NetworkResult.Error("${Constantes.ERROR_INSERTING_PLAYER}${e.message}")
        }
    }

    suspend fun updateConsole(consola: Console) = withContext(dispatcher) {
        try {
            playerConsoleDao.updateConsole(consola.toEntity())
            NetworkResult.Success(Unit)
        } catch (e: Exception) {
            NetworkResult.Error("${Constantes.ERROR_UPDATING_CONSOLE}${e.message}")
        }

    }

}