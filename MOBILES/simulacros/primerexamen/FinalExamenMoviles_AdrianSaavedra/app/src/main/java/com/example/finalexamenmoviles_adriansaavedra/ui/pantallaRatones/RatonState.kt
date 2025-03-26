package com.example.finalexamenmoviles_adriansaavedra.ui.pantallaRatones

import com.example.finalexamenmoviles_adriansaavedra.domain.model.Raton
import com.example.finalexamenmoviles_adriansaavedra.ui.common.UiEvent

data class RatonState(
    val aviso : UiEvent ?= null,
    val isLoading : Boolean = false,
    val rat : List<Raton> = emptyList(),
    val name : String = ""
)
