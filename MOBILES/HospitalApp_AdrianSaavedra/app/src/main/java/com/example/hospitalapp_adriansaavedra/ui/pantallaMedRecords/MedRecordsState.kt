package com.example.hospitalapp_adriansaavedra.ui.pantallaMedRecords

import com.example.hospitalapp_adriansaavedra.domain.modelo.MedRecord
import com.example.hospitalapp_adriansaavedra.ui.common.UiEvent

data class MedRecordsState(
    val isLoading: Boolean = false,
    val patientsRecords: List<MedRecord> = emptyList(),
    val aviso: UiEvent? = null
)