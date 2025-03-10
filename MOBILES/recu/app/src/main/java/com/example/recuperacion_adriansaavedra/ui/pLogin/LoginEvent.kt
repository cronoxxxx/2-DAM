package com.example.recuperacion_adriansaavedra.ui.pLogin

sealed class LoginEvent {
    data class OnUsernameChange(val username: String) : LoginEvent()
    data class OnPasswordChange(val password: String) : LoginEvent()
    data object OnLoginClick : LoginEvent()
    data object OnAvisoVisto : LoginEvent()
}