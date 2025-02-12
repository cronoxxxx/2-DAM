package com.example.hospitalapp_adriansaavedra.ui.common

sealed class UiEvent {
    data object Navigate : UiEvent()
    data class ShowSnackbar(val message: String) : UiEvent()
}