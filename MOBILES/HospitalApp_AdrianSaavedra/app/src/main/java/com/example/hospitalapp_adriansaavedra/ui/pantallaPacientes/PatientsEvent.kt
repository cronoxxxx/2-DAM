package com.example.hospitalapp_adriansaavedra.ui.pantallaPacientes


sealed class PatientsEvent {
    data object GetPatients : PatientsEvent()
    data class OnPlayClick(val patientId: Int) : PatientsEvent()
    data object AvisoVisto : PatientsEvent()
}