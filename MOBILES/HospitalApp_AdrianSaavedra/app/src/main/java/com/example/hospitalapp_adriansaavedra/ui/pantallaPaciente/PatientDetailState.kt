package com.example.hospitalapp_adriansaavedra.ui.pantallaPaciente

import com.example.hospitalapp_adriansaavedra.domain.modelo.MedRecord
import com.example.hospitalapp_adriansaavedra.domain.modelo.Patient
import com.example.hospitalapp_adriansaavedra.ui.common.UiEvent

data class PatientDetailState (
    val isLoading: Boolean = false,
    val patient: Patient? = null,
    val aviso: UiEvent? = null
)