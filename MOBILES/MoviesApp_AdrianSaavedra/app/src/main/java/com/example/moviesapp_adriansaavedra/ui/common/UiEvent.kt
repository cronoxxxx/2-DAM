package com.example.moviesapp_adriansaavedra.ui.common

sealed class UiEvent{
    data class ShowSnackbar(
        val message: String
    ): UiEvent()

}