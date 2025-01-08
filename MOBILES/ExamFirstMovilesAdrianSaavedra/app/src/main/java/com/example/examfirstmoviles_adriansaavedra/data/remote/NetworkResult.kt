package com.example.examfirstmoviles_adriansaavedra.data.remote


sealed class NetworkResult<T> {
    class Success<T>(val data: T) : NetworkResult<T>()
    class Error<T>(val message: String) : NetworkResult<T>()
    inline fun <R> map( transform :(data: T) -> R) =
        when(this){
            is Error -> Error(message)
            is Success -> Success(transform(data))
        }

    inline fun <R> then(transform: (data: T) -> NetworkResult<R>) =
        when (this) {
            is Error -> Error(message)
            is Success -> transform(data)
        }
}