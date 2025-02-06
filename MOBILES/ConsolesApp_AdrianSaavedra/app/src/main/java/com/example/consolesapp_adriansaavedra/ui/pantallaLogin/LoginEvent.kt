package com.example.consolesapp_adriansaavedra.ui.pantallaLogin

import com.example.consolesapp_adriansaavedra.domain.model.Player

sealed class LoginEvent {
    data class OnLoginClick(val player: Player) : LoginEvent()
    data class OnRegisterClick(val player: Player) : LoginEvent()
    object AvisoVisto : LoginEvent()
    data class OnUsernameChange(val value: String) : LoginEvent()
    data class OnPasswordChange(val value: String) : LoginEvent()
}