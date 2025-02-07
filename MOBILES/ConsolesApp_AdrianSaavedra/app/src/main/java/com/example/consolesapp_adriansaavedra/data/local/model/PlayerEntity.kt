package com.example.consolesapp_adriansaavedra.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "jugadores")
data class PlayerEntity(
    @PrimaryKey(autoGenerate = true)
    val jugadorId: Int =0,
    val username: String,
    val password: String
)
