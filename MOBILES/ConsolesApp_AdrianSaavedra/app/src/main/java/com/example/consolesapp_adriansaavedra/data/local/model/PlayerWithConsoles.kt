package com.example.consolesapp_adriansaavedra.data.local.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.consolesapp_adriansaavedra.ui.Constantes

data class PlayerWithConsoles(
    @Embedded val jugador: PlayerEntity,
    @Relation(
        parentColumn = Constantes.PLAYER_ID,
        entityColumn = Constantes.CONSOLE_ID,
        associateBy = Junction(PlayerConsoleCrossRef::class)
    )
    val consolas: List<ConsoleEntity>
)