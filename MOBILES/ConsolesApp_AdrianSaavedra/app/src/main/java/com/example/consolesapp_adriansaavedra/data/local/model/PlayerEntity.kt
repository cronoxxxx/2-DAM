package com.example.consolesapp_adriansaavedra.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.consolesapp_adriansaavedra.ui.Constantes

@Entity(tableName = Constantes.PLAYERS_TABLE)
data class PlayerEntity(
    @PrimaryKey(autoGenerate = true)
    val jugadorId: Int = 0,
    val username: String,
    val password: String
)
