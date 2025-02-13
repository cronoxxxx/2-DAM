package com.example.consolesapp_adriansaavedra.data.local.model

import androidx.room.Entity
import com.example.consolesapp_adriansaavedra.ui.Constantes

@Entity(primaryKeys = [Constantes.PLAYER_ID, Constantes.CONSOLE_ID])
data class PlayerConsoleCrossRef(
    val jugadorId: Int,
    val consolaId: Int,
)