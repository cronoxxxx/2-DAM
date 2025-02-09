package com.example.consolesapp_adriansaavedra.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.consolesapp_adriansaavedra.data.local.model.*

@Dao
interface PlayerConsoleDao {
    @Query("DELETE FROM consolas WHERE consolaId=:idConsole")
    suspend fun deleteConsole(idConsole: Int)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertConsole(consola: ConsoleEntity)

//    @Transaction
//    @Query("SELECT * FROM jugadores")
//    suspend fun getPlayersWithConsoles(): List<PlayerWithConsoles>

    @Query("SELECT * FROM jugadores")
    suspend fun getAllPlayers(): List<PlayerEntity>

    @Query("SELECT * FROM consolas")
    suspend fun getAllConsoles(): List<ConsoleEntity>

    @Transaction
    @Query("SELECT * FROM jugadores WHERE jugadorId = :playerId")
    suspend fun getPlayerWithConsoles(playerId: Int): PlayerWithConsoles

    @Query("SELECT * FROM consolas WHERE consolaId = :consoleId")
    suspend fun getConsole(consoleId: Int): ConsoleEntity

    @Query("DELETE FROM jugadores WHERE jugadorId=:idJugador")
    suspend fun deletePlayer(idJugador: Int)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertPlayer(jugador: PlayerEntity)

    @Insert
    suspend fun insertCrossRef(playerConsoleCrossRef: PlayerConsoleCrossRef)

    @Update
    suspend fun updateConsole(console: ConsoleEntity)

    @Update
    suspend fun updatePlayer(player: PlayerEntity)



}