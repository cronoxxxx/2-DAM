package com.example.hospitalapp_adriansaavedra.data.remote.services

import com.example.hospitalapp_adriansaavedra.domain.modelo.MedRecord
import com.example.hospitalapp_adriansaavedra.domain.modelo.Patient
import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PatientService {
    @GET("patients")
    suspend fun getPatients(): Response<List<Patient>>



    @GET("patients/{patientId}/medRecords")
    suspend fun getPatientRecords(@Path("patientId") id: Int): Response<List<MedRecord>>
}
