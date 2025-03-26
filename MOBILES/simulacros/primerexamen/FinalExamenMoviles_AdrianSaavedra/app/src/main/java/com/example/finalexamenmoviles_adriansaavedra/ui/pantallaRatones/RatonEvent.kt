package com.example.finalexamenmoviles_adriansaavedra.ui.pantallaRatones

sealed class RatonEvent {
    data class AddRat (val name : String) : RatonEvent()

    data object AvisoVisto: RatonEvent()
    data object GetRats : RatonEvent()
    data class OnNameChange(val name :String): RatonEvent()
}