package com.example.apppersonas_adriansaavedra.ui.pantallaPersonas

import com.example.apppersonas_adriansaavedra.domain.modelo.Persona

sealed class PersonaEvent {
    data class GetPersona(val id: Int) : PersonaEvent()
    data class UpdatePersona(val persona: Persona) : PersonaEvent()
    data object DeletePersona : PersonaEvent()
    data object AvisoVisto : PersonaEvent()
}