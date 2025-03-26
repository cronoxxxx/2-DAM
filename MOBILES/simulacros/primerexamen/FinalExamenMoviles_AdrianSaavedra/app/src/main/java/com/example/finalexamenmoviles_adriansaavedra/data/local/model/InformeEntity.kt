package com.example.finalexamenmoviles_adriansaavedra.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "informes")
data class InformeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val nivel: Int
)
