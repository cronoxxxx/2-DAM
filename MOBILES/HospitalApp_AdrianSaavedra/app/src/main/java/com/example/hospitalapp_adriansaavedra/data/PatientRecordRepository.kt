package com.example.hospitalapp_adriansaavedra.data

import com.example.hospitalapp_adriansaavedra.data.remote.NetworkResult
import com.example.hospitalapp_adriansaavedra.data.remote.datasource.PatientRecordsRemoteDataSource
import com.example.hospitalapp_adriansaavedra.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class PatientRecordRepository @Inject constructor(
    private val patientRecordsRemoteDataSource: PatientRecordsRemoteDataSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun fetchPatientRecords(id: Int) = flow {
        emit(NetworkResult.Loading())
        val remoteResult = patientRecordsRemoteDataSource.fetchPatientRecords(id)
        emit(remoteResult)
    }.catch { e -> emit(NetworkResult.Error(e.message ?: e.toString())) }
        .flowOn(ioDispatcher)

    suspend fun fetchSinglePatientRecord(patientId: Int, recordId: Int) = flow {
        emit(NetworkResult.Loading())
        val remoteResult = patientRecordsRemoteDataSource.fetchPatientRecords(patientId)
        if (remoteResult is NetworkResult.Success) {
            val record = remoteResult.data.find { it.id == recordId }
            if (record != null) {
                emit(NetworkResult.Success(record))
            }
        }
    }.catch { e -> emit(NetworkResult.Error(e.message ?: e.toString())) }
        .flowOn(ioDispatcher)
}