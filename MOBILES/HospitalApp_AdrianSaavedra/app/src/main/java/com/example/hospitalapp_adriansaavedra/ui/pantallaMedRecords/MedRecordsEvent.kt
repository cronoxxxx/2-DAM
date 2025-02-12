package com.example.hospitalapp_adriansaavedra.ui.pantallaMedRecords

sealed class MedRecordsEvent {
    data class GetPatientRecords(val patientId: Int) : MedRecordsEvent()
    data class OnPlayClick(val recordId: Int) : MedRecordsEvent()
    data object AvisoVisto : MedRecordsEvent()
}
