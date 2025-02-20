package com.example.playersapp_adriansaavedra.ui.pantallaLogin

import com.example.playersapp_adriansaavedra.data.remote.model.Login
import com.example.playersapp_adriansaavedra.data.remote.model.Register
import com.example.playersapp_adriansaavedra.ui.common.UiEvent

data class LoginState(
    val aviso: UiEvent? = null,
    val login: Login = Login(),
    val register: Register = Register(),
    val isLoading: Boolean = false
)