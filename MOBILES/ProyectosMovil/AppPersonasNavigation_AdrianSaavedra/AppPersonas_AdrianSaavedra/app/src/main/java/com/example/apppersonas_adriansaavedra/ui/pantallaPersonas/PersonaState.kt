
package com.example.apppersonas_adriansaavedra.ui.pantallaPersonas

import com.example.apppersonas_adriansaavedra.domain.modelo.Persona
import com.example.apppersonas_adriansaavedra.ui.common.UiEvent

data class PersonaState (
    val persona : Persona = Persona(),
    val aviso: UiEvent? = null
)