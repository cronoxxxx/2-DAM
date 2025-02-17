package com.example.playersapp_adriansaavedra.data.remote.datasource

import com.example.playersapp_adriansaavedra.data.remote.NetworkResult
import com.example.playersapp_adriansaavedra.data.remote.utils.ApiError
import com.example.playersapp_adriansaavedra.data.remote.utils.TokenRefresher
import com.google.gson.Gson
import retrofit2.Response

abstract class BaseApiResponse(
    private val tokenRefresher: TokenRefresher
) {
    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): NetworkResult<T> {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                return if (body != null) {
                    NetworkResult.Success(body)
                } else {
                    @Suppress("UNCHECKED_CAST")
                    NetworkResult.Success(Unit as T)
                }
            } else if (response.code() == 401) {
                return when (tokenRefresher()) {
                    is NetworkResult.Success -> {
                        safeApiCall(apiCall)
                    }

                    is NetworkResult.Error -> {
                        NetworkResult.Error("Session expired. Please log in again.")
                    }
                }
            }
            response.errorBody()?.let {
                val errorBody = Gson().fromJson(it.charStream(), ApiError::class.java)
                return NetworkResult.Error(errorBody.message)
            }
            return NetworkResult.Error("${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return NetworkResult.Error(e.message ?: e.toString())
        }
    }
}


