package com.example.hospitalapp_adriansaavedra.ui.pantallaLogin

sealed class LoginEvent {
    data class OnLoginClick(val patientId: String) : LoginEvent()
    data object AvisoVisto : LoginEvent()
    data class OnIdLoginChange(val value: String) : LoginEvent()
}
