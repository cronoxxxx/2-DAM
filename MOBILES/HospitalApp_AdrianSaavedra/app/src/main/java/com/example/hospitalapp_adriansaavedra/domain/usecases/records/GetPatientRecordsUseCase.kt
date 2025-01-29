package com.example.hospitalapp_adriansaavedra.domain.usecases.records

import com.example.hospitalapp_adriansaavedra.data.PatientRecordRepository
import javax.inject.Inject

class GetPatientRecordsUseCase @Inject constructor(private val patientRecordRepository: PatientRecordRepository) {
    suspend operator fun invoke(id: Int) = patientRecordRepository.fetchPatientRecords(id)
}