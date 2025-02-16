package com.example.playersapp_adriansaavedra.data.remote.utils

import com.example.playersapp_adriansaavedra.data.PreferencesRepository
import com.example.playersapp_adriansaavedra.data.remote.services.AuthService
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.*
import javax.inject.Inject

class AuthAuthenticator @Inject constructor(
    private val preferencesRepository: PreferencesRepository,
    private val authService: Lazy<AuthService>
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        return runBlocking {
            val token = preferencesRepository.token.first()
            val newToken = getNewToken(token)

            if (!newToken.isSuccessful || newToken.body() == null) {
                preferencesRepository.deleteToken()
                null
            } else {
                newToken.body()?.let {
                    preferencesRepository.saveToken(it.accessToken)
                    response.request.newBuilder()
                        .header("Authorization", "Bearer ${it.accessToken}")
                        .build()
                }
            }
        }
    }

    private suspend fun getNewToken(refreshToken: String?): retrofit2.Response<AuthenticationResponse> {
        return authService.value.refreshToken(RefreshTokenRequest(refreshToken ?: ""))
    }
}