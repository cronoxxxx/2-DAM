package com.example.usersapp_adriansaavedra.ui.pantallaUsuario

import com.example.usersapp_adriansaavedra.domain.modelo.User

sealed class DetalleEvent {
    data class GetUser(val id: Int) : DetalleEvent()
    data object AvisoVisto : DetalleEvent()
    data class UpdateUser(val user: User) : DetalleEvent()
    data class DeleteUser(val id: Int) : DetalleEvent()
}