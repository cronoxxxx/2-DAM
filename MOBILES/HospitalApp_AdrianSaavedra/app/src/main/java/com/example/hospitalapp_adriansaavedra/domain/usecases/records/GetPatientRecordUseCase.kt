package com.example.hospitalapp_adriansaavedra.domain.usecases.records

import com.example.hospitalapp_adriansaavedra.data.PatientRepository
import javax.inject.Inject

class GetPatientRecordUseCase @Inject constructor(private val patientRepository: PatientRepository) {
    suspend operator fun invoke(patientId: Int, recordId: Int)= patientRepository.fetchSinglePatientRecord(patientId, recordId)
}