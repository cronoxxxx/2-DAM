package com.example.consolesapp_adriansaavedra.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.consolesapp_adriansaavedra.data.local.model.ConsoleEntity
import com.example.consolesapp_adriansaavedra.data.local.model.PlayerConsoleCrossRef
import com.example.consolesapp_adriansaavedra.data.local.model.PlayerEntity
import com.example.consolesapp_adriansaavedra.data.local.model.PlayerWithConsoles

@Dao
interface PlayerConsoleDao {
    @Query("DELETE FROM consolas WHERE consolaId=:idConsole")
    suspend fun deleteConsole(idConsole: Int)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertConsole(console: ConsoleEntity): Long

    @Query("SELECT * FROM jugadores")
    suspend fun getAllPlayers(): List<PlayerEntity>

    @Query("SELECT * FROM consolas")
    suspend fun getAllConsoles(): List<ConsoleEntity>


    @Query("SELECT * FROM jugadores WHERE jugadorId = :playerId")
    suspend fun getPlayerWithConsoles(playerId: Int): PlayerWithConsoles

    @Query("SELECT * FROM consolas WHERE consolaId = :consoleId")
    suspend fun getConsole(consoleId: Int): ConsoleEntity



    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertPlayer(jugador: PlayerEntity)

    @Insert
    suspend fun insertCrossRef(playerConsoleCrossRef: PlayerConsoleCrossRef)


    @Update
    suspend fun updateConsole(console: ConsoleEntity)


}