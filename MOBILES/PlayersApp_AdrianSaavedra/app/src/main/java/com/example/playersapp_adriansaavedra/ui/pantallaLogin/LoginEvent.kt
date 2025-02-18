package com.example.playersapp_adriansaavedra.ui.pantallaLogin

sealed class LoginEvent {
    data class OnLoginClick(val username: String, val password: String) : LoginEvent()
    data class OnRegisterClick(val username: String, val password: String, val email: String) :
        LoginEvent()

    data class OnEmailRegisterChange(val email: String) : LoginEvent()
    data class OnPasswordLoginChange(val password: String) : LoginEvent()
    data class OnUsernameLoginChange(val username: String) : LoginEvent()
    data class OnPasswordRegisterChange(val password: String) : LoginEvent()
    data class OnUsernameRegisterChange(val username: String) : LoginEvent()
    data object OnAvisoVisto : LoginEvent()
}