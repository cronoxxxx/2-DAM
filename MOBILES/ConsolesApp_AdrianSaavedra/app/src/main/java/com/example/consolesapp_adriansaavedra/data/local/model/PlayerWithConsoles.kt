package com.example.consolesapp_adriansaavedra.data.local.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class PlayerWithConsoles (
    @Embedded val jugador: PlayerEntity,
    @Relation(
        parentColumn = "jugadorId",
        entityColumn = "consolaId",
        associateBy = Junction(PlayerConsoleCrossRef::class)
    )
    val consolas: List<ConsoleEntity>
)