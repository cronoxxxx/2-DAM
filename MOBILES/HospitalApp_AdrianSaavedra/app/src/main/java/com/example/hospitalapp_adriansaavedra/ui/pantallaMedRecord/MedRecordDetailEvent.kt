package com.example.hospitalapp_adriansaavedra.ui.pantallaMedRecord

sealed class MedRecordDetailEvent {
    data class GetPatientRecord(val patientId: Int, val recordId: Int) : MedRecordDetailEvent()
    data object AvisoVisto : MedRecordDetailEvent()
}