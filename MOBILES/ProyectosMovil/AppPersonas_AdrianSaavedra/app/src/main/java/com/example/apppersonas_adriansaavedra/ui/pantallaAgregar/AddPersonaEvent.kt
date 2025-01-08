package com.example.apppersonas_adriansaavedra.ui.pantallaAgregar

import com.example.apppersonas_adriansaavedra.domain.modelo.Persona

sealed class AddPersonaEvent {
    data class AddPersona(val persona: Persona) : AddPersonaEvent()
    data object AvisoVisto : AddPersonaEvent()
}