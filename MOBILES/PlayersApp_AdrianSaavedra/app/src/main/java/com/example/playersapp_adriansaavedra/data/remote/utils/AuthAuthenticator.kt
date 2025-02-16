package com.example.playersapp_adriansaavedra.data.remote.utils

import com.example.playersapp_adriansaavedra.data.PreferencesRepository
import com.example.playersapp_adriansaavedra.data.remote.services.LoginService
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthAuthenticator @Inject constructor(
    private val preferencesRepository: PreferencesRepository,
    private val loginService: Lazy<LoginService>
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        return runBlocking {
            val refreshToken = preferencesRepository.refreshToken.first()
            val newTokenResponse = loginService.value.refreshToken(RefreshTokenRequest(refreshToken ?: ""))

            if (newTokenResponse.isSuccessful && newTokenResponse.body() != null) {
                val newTokens = newTokenResponse.body()!!
                preferencesRepository.saveToken(newTokens.accessToken)
                preferencesRepository.saveRefreshToken(newTokens.refreshToken)
                response.request.newBuilder()
                    .header("Authorization", "Bearer ${newTokens.accessToken}")
                    .build()
            } else {
                preferencesRepository.deleteToken()
                null
            }
        }
    }
}
