package com.example.hospitalapp_adriansaavedra.data.remote.datasource

import com.example.hospitalapp_adriansaavedra.data.remote.services.PatientRecordService
import javax.inject.Inject

class PatientRecordsRemoteDataSource @Inject constructor(
    private val patientRecordService: PatientRecordService
) : BaseApiResponse() {
    suspend fun fetchPatientRecords(id: Int) =
        safeApiCall { patientRecordService.getPatientRecords(id) }
}