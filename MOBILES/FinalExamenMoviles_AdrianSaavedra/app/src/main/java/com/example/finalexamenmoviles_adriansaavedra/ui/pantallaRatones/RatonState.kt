package com.example.finalexamenmoviles_adriansaavedra.ui.pantallaRatones

import com.example.finalexamenmoviles_adriansaavedra.data.remote.model.Alumno
import com.example.finalexamenmoviles_adriansaavedra.data.remote.model.Raton
import com.example.finalexamenmoviles_adriansaavedra.ui.common.UiEvent

data class RatonState(
    val aviso : UiEvent ?= null,
    val isLoading : Boolean = false,
    val rat : List<Raton> = emptyList(),
)
