package com.example.consolesapp_adriansaavedra.domain.model


data class Player(
    val jugadorId: Int = 0,
    val username: String = "",
    val password: String = "",
    val consolasList: List<Console> = emptyList()
)

