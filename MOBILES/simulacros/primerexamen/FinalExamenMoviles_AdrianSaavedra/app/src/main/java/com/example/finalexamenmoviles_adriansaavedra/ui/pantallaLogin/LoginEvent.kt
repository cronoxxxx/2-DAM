package com.example.finalexamenmoviles_adriansaavedra.ui.pantallaLogin

sealed class LoginEvent {
    data class OnUsernameChange(val username: String) : LoginEvent()
    data class OnPasswordChange(val password: String) : LoginEvent()
    data object OnLoginClick : LoginEvent()
    data object OnAvisoVisto : LoginEvent()
}
