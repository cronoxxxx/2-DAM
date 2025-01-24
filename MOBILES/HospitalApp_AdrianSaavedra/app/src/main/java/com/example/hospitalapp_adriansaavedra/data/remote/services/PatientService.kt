package com.example.hospitalapp_adriansaavedra.data.remote.services

import com.example.hospitalapp_adriansaavedra.domain.modelo.Patient
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PatientService {
    @GET("patients")
    suspend fun getPatients() : Response<List<Patient>>
    @GET("patients")
    suspend fun getPatient(@Query("id") id: Int) : Response<Patient>
}