package com.example.usersapp_adriansaavedra.ui.pantallaAgregar

import com.example.usersapp_adriansaavedra.domain.modelo.User

sealed class AddUsuarioEvent {
    data class AddUser(val user: User) : AddUsuarioEvent()
    data object AvisoVisto : AddUsuarioEvent()

}