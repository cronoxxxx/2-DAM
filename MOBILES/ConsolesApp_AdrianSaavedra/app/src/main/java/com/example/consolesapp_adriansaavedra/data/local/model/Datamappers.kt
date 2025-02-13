package com.example.consolesapp_adriansaavedra.data.local.model

import com.example.consolesapp_adriansaavedra.domain.model.Console
import com.example.consolesapp_adriansaavedra.domain.model.Player

fun Console.toEntity() =
    ConsoleEntity(
        consolaId = this.consolaId,
        nombre = this.nombre,
        modelo = this.modelo,
        precio = this.precio
    )

fun ConsoleEntity.toConsole() =
    Console(
        consolaId = this.consolaId,
        nombre = this.nombre,
        modelo = this.modelo,
        precio = this.precio
    )

fun PlayerEntity.toPlayer() = Player(
    jugadorId = this.jugadorId,
    username = this.username,
    password = this.password,
    consolasList = emptyList()
)

fun Player.toEntity() = PlayerEntity(
    jugadorId = this.jugadorId,
    username = this.username,
    password = this.password
)

fun PlayerWithConsoles.toPlayer() = Player(
    jugador.toPlayer().jugadorId,
    jugador.toPlayer().username,
    jugador.toPlayer().password,
    consolas.map { it.toConsole() })

fun Player.validate() = username.isNotBlank() && password.isNotBlank()
fun Console.validate() = nombre.isNotBlank() && modelo.isNotBlank()

