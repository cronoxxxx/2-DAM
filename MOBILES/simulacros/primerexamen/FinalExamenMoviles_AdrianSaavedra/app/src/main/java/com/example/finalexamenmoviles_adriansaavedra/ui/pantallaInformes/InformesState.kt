package com.example.finalexamenmoviles_adriansaavedra.ui.pantallaInformes

import com.example.finalexamenmoviles_adriansaavedra.domain.model.Informe
import com.example.finalexamenmoviles_adriansaavedra.ui.common.UiEvent

data class InformesState(
    val informes: List<Informe> = emptyList(),
    val isLoading: Boolean = false,
    val aviso: UiEvent? = null,
    val selectedInformeId: Int = 0
)