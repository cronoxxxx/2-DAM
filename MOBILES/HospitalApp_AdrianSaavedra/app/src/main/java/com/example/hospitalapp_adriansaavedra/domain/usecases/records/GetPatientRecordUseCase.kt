package com.example.hospitalapp_adriansaavedra.domain.usecases.records

import com.example.hospitalapp_adriansaavedra.data.PatientRecordRepository
import javax.inject.Inject

class GetPatientRecordUseCase @Inject constructor(private val patientRecordRepository: PatientRecordRepository) {
    suspend operator fun invoke(patientId: Int, recordId: Int) =
        patientRecordRepository.fetchSinglePatientRecord(patientId, recordId)
}