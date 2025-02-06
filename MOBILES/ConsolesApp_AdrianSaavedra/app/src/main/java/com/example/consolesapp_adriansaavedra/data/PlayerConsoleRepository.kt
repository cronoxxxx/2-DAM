package com.example.consolesapp_adriansaavedra.data

import com.example.consolesapp_adriansaavedra.data.local.PlayerConsoleDao
import com.example.consolesapp_adriansaavedra.data.local.model.PlayerConsoleCrossRef
import com.example.consolesapp_adriansaavedra.data.local.model.toEntity
import com.example.consolesapp_adriansaavedra.data.local.model.toPlayer
import com.example.consolesapp_adriansaavedra.domain.model.Console
import com.example.consolesapp_adriansaavedra.domain.model.Player
import javax.inject.Inject

class PlayerConsoleRepository @Inject constructor(private val playerConsoleDao: PlayerConsoleDao) {
    suspend fun deleteConsole(id: Int) = playerConsoleDao.deleteConsole(id)
    suspend fun deletePlayer(id: Int) = playerConsoleDao.deletePlayer(id)
    suspend fun fetchAllPlayers() = playerConsoleDao.getAllPlayers().map { it.toPlayer() }
    suspend fun fetchPlayerWithConsoles(id: Int) =
        playerConsoleDao.getPlayerWithConsoles(id).toPlayer()

    suspend fun insertConsole(consola: Console) = playerConsoleDao.insertConsole(consola.toEntity())
    suspend fun insertPlayer(player: Player) = playerConsoleDao.insertPlayer(player.toEntity())
    suspend fun insertCrossRef(playerId: Int, consoleId: Int) =
        playerConsoleDao.insertCrossRef(PlayerConsoleCrossRef(playerId, consoleId))

    suspend fun updateConsole(consola: Console) = playerConsoleDao.updateConsole(consola.toEntity())
    suspend fun updatePlayer(player: Player)=playerConsoleDao.updatePlayer(player.toEntity())


}