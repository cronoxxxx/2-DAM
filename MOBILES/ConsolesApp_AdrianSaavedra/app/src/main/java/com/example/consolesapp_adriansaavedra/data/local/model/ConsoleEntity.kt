package com.example.consolesapp_adriansaavedra.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "consolas")
data class ConsoleEntity(
    @PrimaryKey(autoGenerate = true)
    val consolaId: Int =0,
    val nombre: String,
    val modelo: String,
    val precio: Double,
)