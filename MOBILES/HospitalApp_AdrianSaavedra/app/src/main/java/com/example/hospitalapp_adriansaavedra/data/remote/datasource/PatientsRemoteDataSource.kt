package com.example.hospitalapp_adriansaavedra.data.remote.datasource

import com.example.hospitalapp_adriansaavedra.data.remote.services.PatientService
import javax.inject.Inject

class PatientsRemoteDataSource @Inject constructor(
    private val patientService: PatientService
) : BaseApiResponse() {

    suspend fun fetchPatients() = safeApiCall { patientService.getPatients() }

}