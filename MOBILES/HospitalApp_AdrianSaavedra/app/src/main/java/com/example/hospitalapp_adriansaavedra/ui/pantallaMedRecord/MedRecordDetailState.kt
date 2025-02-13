package com.example.hospitalapp_adriansaavedra.ui.pantallaMedRecord

import com.example.hospitalapp_adriansaavedra.domain.modelo.MedRecord
import com.example.hospitalapp_adriansaavedra.ui.common.UiEvent

data class MedRecordDetailState(
    val isLoading: Boolean = false,
    val medRecord: MedRecord? = null,
    val aviso: UiEvent? = null
)