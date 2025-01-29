package com.example.hospitalapp_adriansaavedra.ui.pantallaPacientes

import com.example.hospitalapp_adriansaavedra.domain.modelo.Patient
import com.example.hospitalapp_adriansaavedra.ui.common.UiEvent

data class PatientsState(
    val patients: List<Patient> = emptyList(),
    val aviso: UiEvent? = null,
    val isLoading: Boolean = false,
    val selectedPatientId: Int = 0
)