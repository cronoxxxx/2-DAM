package com.example.examfirstmoviles_adriansaavedra.data.remote.datasource

import com.example.examfirstmoviles_adriansaavedra.data.remote.NetworkResult
import retrofit2.HttpException
import retrofit2.Response
import timber.log.Timber

abstract class BaseApiResponse {
    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): NetworkResult<T> {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return NetworkResult.Success(body)
                }
            }
            return error("${response.code()} ${response.message()}")
        } catch (e: HttpException) {
            Timber.e(e.message())
            return error(e.message ?: e.toString())
        }
    }
    private fun <T> error(errorMessage: String): NetworkResult<T> = NetworkResult.Error(errorMessage)
}