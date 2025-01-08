
package com.example.segundaapp_adriansaavedra.ui.pantallaMain

import com.example.segundaapp_adriansaavedra.domain.modelo.Persona

data class MainState (
    val persona : Persona = Persona(),
    val aviso : String? = null,
    val siguiente: Boolean? =true,
    val anterior : Boolean? = true,
    val updatedelete : Boolean? = true,
    val indiceActual : Int = 0,
    val longitudLista: Int = 0
)