package com.example.finalexamenmoviles_adriansaavedra.ui.pantallaLogin

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    object Success : LoginState()
    data class Error(val message: String) : LoginState()
}