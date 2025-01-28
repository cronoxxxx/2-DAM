package com.example.hospitalapp_adriansaavedra.ui.pantallaPacientes

sealed class PatientsEvent {
    data object GetPatients : PatientsEvent()
}