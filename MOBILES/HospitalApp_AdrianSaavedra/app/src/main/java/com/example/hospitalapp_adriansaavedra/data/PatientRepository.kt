package com.example.hospitalapp_adriansaavedra.data

import com.example.hospitalapp_adriansaavedra.data.remote.NetworkResult
import com.example.hospitalapp_adriansaavedra.data.remote.datasource.PatientsRemoteDataSource
import com.example.hospitalapp_adriansaavedra.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class PatientRepository @Inject constructor(
    private val patientsRemoteDataSource: PatientsRemoteDataSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun fetchPatients() = flow {
        emit(NetworkResult.Loading())
        val remoteResult = patientsRemoteDataSource.fetchPatients()
        emit(remoteResult)
    }.catch { e -> emit(NetworkResult.Error(e.message ?: e.toString())) }
        .flowOn(ioDispatcher)

    suspend fun fetchPatient(id: Int) = flow {
        emit(NetworkResult.Loading())
        val remoteResult = patientsRemoteDataSource.fetchPatients()
        if (remoteResult is NetworkResult.Success) {
            val patient = remoteResult.data.find { it.id == id }
            if (patient != null) {
                emit(NetworkResult.Success(patient))
            }
        }
    }.catch { e -> emit(NetworkResult.Error(e.message ?: e.toString())) }
        .flowOn(ioDispatcher)
}