package com.example.finalexamenmoviles_adriansaavedra.data.remote.utils


import com.example.finalexamenmoviles_adriansaavedra.data.PreferencesRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject


class AuthInterceptor @Inject constructor(
    private val preferencesRepository: PreferencesRepository,
    private val tokenExpirationHandler: TokenExpirationHandler
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking { preferencesRepository.token.first() }

        val request = if (!token.isNullOrEmpty()) {
            chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        } else {
            chain.request()
        }

        val response = chain.proceed(request)


        if (!response.isSuccessful ) {
                runBlocking {
                    tokenExpirationHandler.onTokenExpired()
                }

        }

        return response
    }
}