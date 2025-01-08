package com.example.apppersonas_adriansaavedra.ui.pantallaMain

import com.example.apppersonas_adriansaavedra.domain.modelo.Persona

sealed class MainEvent {
    data object GetPersonas : MainEvent()
    data object AvisoVisto : MainEvent()
    data class DeletePersona(val persona: Persona) : MainEvent()
    data class SeleccionaPersona(val persona: Persona) : MainEvent()
    data object DeletePersonasSeleccionadas : MainEvent()
    data object StartSelectMode : MainEvent()
    data object ResetSelectMode : MainEvent()
    data class RemovePersonaFromSelection(val persona: Persona) : MainEvent()
    data object AvisoSelected : MainEvent()
}