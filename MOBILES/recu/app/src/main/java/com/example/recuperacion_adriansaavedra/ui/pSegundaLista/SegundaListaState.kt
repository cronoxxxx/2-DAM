package com.example.recuperacion_adriansaavedra.ui.pSegundaLista

import com.example.finalexamenmoviles_adriansaavedra.ui.common.UiEvent

// Estado para la segunda pantalla de lista
data class SegundaListaState(
    val aviso: UiEvent? = null,
    val isLoading: Boolean = false,
    val items: List<String> = emptyList() // Reemplazar con tu modelo de datos
)