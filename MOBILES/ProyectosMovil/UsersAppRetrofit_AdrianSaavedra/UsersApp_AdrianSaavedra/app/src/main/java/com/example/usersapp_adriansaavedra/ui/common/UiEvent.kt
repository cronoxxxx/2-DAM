package com.example.usersapp_adriansaavedra.ui.common

sealed class UiEvent {
    data object PopBackStack: UiEvent()
    data class ShowSnackbar(
        val message: String
    ) : UiEvent()

}