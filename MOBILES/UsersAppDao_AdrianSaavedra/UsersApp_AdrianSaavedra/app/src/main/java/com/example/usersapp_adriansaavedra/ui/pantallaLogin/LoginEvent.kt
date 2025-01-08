package com.example.usersapp_adriansaavedra.ui.pantallaLogin

import com.example.usersapp_adriansaavedra.domain.modelo.User

sealed class LoginEvent {
    data class RegisterUser(val user: User) : LoginEvent()
    data class LoginUser(val user: User) : LoginEvent()
    data object AvisoVisto : LoginEvent()
}