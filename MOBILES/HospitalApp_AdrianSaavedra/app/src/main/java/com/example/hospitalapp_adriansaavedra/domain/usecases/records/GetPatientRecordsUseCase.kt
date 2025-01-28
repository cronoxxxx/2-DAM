package com.example.hospitalapp_adriansaavedra.domain.usecases.records

import com.example.hospitalapp_adriansaavedra.data.PatientRepository
import javax.inject.Inject

class GetPatientRecordsUseCase @Inject constructor(private val patientRepository: PatientRepository) {
    suspend operator fun invoke(id: Int)= patientRepository.fetchPatientRecords(id)
}