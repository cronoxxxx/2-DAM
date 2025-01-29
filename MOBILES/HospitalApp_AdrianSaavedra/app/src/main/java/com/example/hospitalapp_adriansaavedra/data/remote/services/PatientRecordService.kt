package com.example.hospitalapp_adriansaavedra.data.remote.services

import com.example.hospitalapp_adriansaavedra.domain.modelo.MedRecord
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

fun interface PatientRecordService {
    @GET("patients/{patientId}/medRecords")
    suspend fun getPatientRecords(@Path("patientId") id: Int): Response<List<MedRecord>>
}