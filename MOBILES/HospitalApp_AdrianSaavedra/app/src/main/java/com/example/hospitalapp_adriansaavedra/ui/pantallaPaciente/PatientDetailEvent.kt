package com.example.hospitalapp_adriansaavedra.ui.pantallaPaciente

sealed class PatientDetailEvent {
    data class GetPatient(val id: Int) : PatientDetailEvent()
    data object AvisoVisto : PatientDetailEvent()
}