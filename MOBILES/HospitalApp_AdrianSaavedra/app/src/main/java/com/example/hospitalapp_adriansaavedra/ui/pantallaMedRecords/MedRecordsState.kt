package com.example.hospitalapp_adriansaavedra.ui.pantallaMedRecords

import com.example.hospitalapp_adriansaavedra.domain.modelo.MedRecord
import com.example.hospitalapp_adriansaavedra.ui.common.UiEvent

data class MedRecordsState(
    val patientsRecords: List<MedRecord> = emptyList(),
    val isLoading: Boolean = false,
    val aviso: UiEvent? = null,
    val selectedRecordId: Int = 0
)
