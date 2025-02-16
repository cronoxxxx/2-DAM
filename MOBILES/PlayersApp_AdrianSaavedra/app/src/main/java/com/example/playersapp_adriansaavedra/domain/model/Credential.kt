package com.example.playersapp_adriansaavedra.domain.model

data class Credential(
    val id: Int = 0,
    val username: String,
    val password: String,
    val email: String,
    val favoritePlayers : List<Player> = emptyList(),
)