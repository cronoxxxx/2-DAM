package com.example.finalexamenmoviles_adriansaavedra.ui.pantallaDetalleInformes

import com.example.finalexamenmoviles_adriansaavedra.domain.model.Informe
import com.example.finalexamenmoviles_adriansaavedra.ui.common.UiEvent

data class InformeDetailState(
    val informe: Informe = Informe(),
    val isLoading: Boolean = false,
    val aviso: UiEvent? = null
)