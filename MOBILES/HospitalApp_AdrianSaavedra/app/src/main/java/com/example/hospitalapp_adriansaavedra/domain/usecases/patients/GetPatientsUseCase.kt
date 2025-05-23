package com.example.hospitalapp_adriansaavedra.domain.usecases.patients

import com.example.hospitalapp_adriansaavedra.data.PatientRepository
import javax.inject.Inject

class GetPatientsUseCase @Inject constructor(private val patientRepository: PatientRepository) {
    suspend operator fun invoke() = patientRepository.fetchPatients()
}