package com.example.recuperacion_adriansaavedra.data.remote.utils

import com.example.recuperacion_adriansaavedra.data.PreferencesRepository
import com.example.recuperacion_adriansaavedra.data.remote.services.LoginService
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.*
import javax.inject.Inject

class AuthAuthenticator @Inject constructor(
    private val preferencesRepository: PreferencesRepository,
    private val loginService: Lazy<LoginService>
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        return runBlocking {
            val token = preferencesRepository.token.first()
            if (token == null) {
                preferencesRepository.clearTokens()
                null
            } else {
                response.request.newBuilder()
                    .header("Authorization", "Bearer $token")
                    .build()
            }
        }
    }
}