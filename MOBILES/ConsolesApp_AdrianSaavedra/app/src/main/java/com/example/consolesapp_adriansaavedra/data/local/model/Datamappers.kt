package com.example.consolesapp_adriansaavedra.data.local.model

import com.example.consolesapp_adriansaavedra.domain.model.Console
import com.example.consolesapp_adriansaavedra.domain.model.Player

fun Console.toEntity() =
    ConsoleEntity(nombre = this.nombre, modelo = this.modelo, precio = this.precio)

fun ConsoleEntity.toConsola() =
    Console(consolaId = this.consolaId ?:0, nombre = this.nombre, modelo = this.modelo, precio = this.precio)

fun PlayerEntity.toPlayer() = Player(
    jugadorId = this.jugadorId ?: 0,
    username = this.username,
    password = this.password,
    consolasList = emptyList()
)

fun Player.toEntity() = PlayerEntity(
    jugadorId = this.jugadorId,
    username = this.username,
    password = this.password
)

fun PlayerWithConsoles.toPlayer(): Player {
    return Player(jugador.toPlayer().jugadorId, jugador.toPlayer().username, jugador.toPlayer().password, consolas.map { it.toConsola() })
}

fun Player.validate()= username.isNotBlank() && password.isNotBlank()


fun Player.toPlayerWithConsoles() = PlayerWithConsoles(
    PlayerEntity(
        username = this.username,
        password = this.password
    ), this.consolasList.map { it.toEntity() })
