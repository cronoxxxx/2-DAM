package com.example.playersapp_adriansaavedra.ui.common

sealed class UiEvent {
    data object Navigate : UiEvent()
    data class ShowSnackbar(val message: String) : UiEvent()
}