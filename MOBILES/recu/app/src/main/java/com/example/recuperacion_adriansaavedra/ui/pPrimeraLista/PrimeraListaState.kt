package com.example.recuperacion_adriansaavedra.ui.pPrimeraLista

import com.example.finalexamenmoviles_adriansaavedra.ui.common.UiEvent

data class PrimeraListaState(
    val aviso: UiEvent? = null,
    val isLoading: Boolean = false,
    val items: List<String> = emptyList() // Reemplazar con tu modelo de datos
)