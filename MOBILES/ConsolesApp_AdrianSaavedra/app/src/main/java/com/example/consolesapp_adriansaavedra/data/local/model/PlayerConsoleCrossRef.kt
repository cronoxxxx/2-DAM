package com.example.consolesapp_adriansaavedra.data.local.model

import androidx.room.Entity

@Entity(primaryKeys = ["jugadorId", "consolaId"])
data class PlayerConsoleCrossRef(
    val jugadorId: Int,
    val consolaId: Int,
)