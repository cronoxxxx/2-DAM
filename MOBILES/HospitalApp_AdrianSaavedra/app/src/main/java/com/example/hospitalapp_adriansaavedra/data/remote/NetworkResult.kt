package com.example.hospitalapp_adriansaavedra.data.remote

sealed class NetworkResult<T> {
    class Success<T>(val data: T) : NetworkResult<T>()
    class Error<T>(val message: String) : NetworkResult<T>()
    class Loading<T> : NetworkResult<T>()

    inline fun <R> map(transform: (data: T) -> R) =
        when (this) {
            is Error -> Error(message)
            is Loading -> Loading()
            is Success -> Success(transform(data))
        }
}