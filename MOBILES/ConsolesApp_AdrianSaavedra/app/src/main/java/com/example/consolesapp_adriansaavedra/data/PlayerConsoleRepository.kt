package com.example.consolesapp_adriansaavedra.data

import com.example.consolesapp_adriansaavedra.data.local.PlayerConsoleDao
import com.example.consolesapp_adriansaavedra.data.local.model.PlayerConsoleCrossRef
import com.example.consolesapp_adriansaavedra.data.local.model.toConsole
import com.example.consolesapp_adriansaavedra.data.local.model.toEntity
import com.example.consolesapp_adriansaavedra.data.local.model.toPlayer
import com.example.consolesapp_adriansaavedra.di.IoDispatcher
import com.example.consolesapp_adriansaavedra.domain.model.Console
import com.example.consolesapp_adriansaavedra.domain.model.Player
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
            NetworkResult.Error("Error deleting console: ${e.message}")
        }
    }

    suspend fun deletePlayer(id: Int) = playerConsoleDao.deletePlayer(id)
    suspend fun fetchAllPlayers() = withContext(dispatcher) {
        try {
            val players = playerConsoleDao.getAllPlayers().map { it.toPlayer() }
            if (players.isEmpty()) {
                NetworkResult.Error("No players found")
            } else {
                NetworkResult.Success(players)
            }
        } catch (e: Exception) {
            NetworkResult.Error("Error fetching players: ${e.message}")
        }
    }

    suspend fun fetchPlayerWithConsoles(id: Int) = withContext(dispatcher) {
        try {
            val result = playerConsoleDao.getPlayerWithConsoles(id).toPlayer()
            if (result.consolasList.isEmpty()) {
                NetworkResult.Error("No consoles found for player")
            } else {
                NetworkResult.Success(result)
            }
            NetworkResult.Success(result)
        } catch (e: Exception) {
            NetworkResult.Error("Error fetching player with consoles: ${e.message}")
        }

    }


    suspend fun fetchConsole(id: Int) = withContext(dispatcher) {
        try {
            val result = playerConsoleDao.getConsole(id).toConsole()
            NetworkResult.Success(result)
        } catch (e: Exception) {
            NetworkResult.Error("Error fetching console: ${e.message}")
        }
    }

    suspend fun insertConsole(consola: Console) = playerConsoleDao.insertConsole(consola.toEntity())
    suspend fun insertPlayer(player: Player): NetworkResult<Unit> = withContext(dispatcher) {
        try {
            val result = playerConsoleDao.getAllPlayers()
            val existingUser = result.find { it.username == player.username }
            if (existingUser == null) {
                playerConsoleDao.insertPlayer(player.toEntity())
                NetworkResult.Success(Unit)
            } else {
                NetworkResult.Error("User already exists")
            }
        } catch (e: Exception) {
            NetworkResult.Error("Error inserting player: ${e.message}")
        }
    }

    suspend fun insertCrossRef(playerId: Int, consoleId: Int) =
        playerConsoleDao.insertCrossRef(PlayerConsoleCrossRef(playerId, consoleId))

    suspend fun updateConsole(consola: Console) = withContext(dispatcher) {
        try {
            playerConsoleDao.updateConsole(consola.toEntity())
            NetworkResult.Success(Unit)
        } catch (e: Exception) {
            NetworkResult.Error("Error updating console: ${e.message}")
        }

    }

    suspend fun updatePlayer(player: Player) = playerConsoleDao.updatePlayer(player.toEntity())


}