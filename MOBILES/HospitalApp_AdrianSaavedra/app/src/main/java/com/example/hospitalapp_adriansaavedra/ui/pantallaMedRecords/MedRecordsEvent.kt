package com.example.hospitalapp_adriansaavedra.ui.pantallaMedRecords

sealed class MedRecordsEvent {
    data class GetPatientRecords (val id: Int) : MedRecordsEvent()
}