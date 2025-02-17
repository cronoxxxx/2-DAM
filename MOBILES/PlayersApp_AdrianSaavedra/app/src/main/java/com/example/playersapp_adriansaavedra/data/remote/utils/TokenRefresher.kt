package com.example.playersapp_adriansaavedra.data.remote.utils

import com.example.playersapp_adriansaavedra.data.PreferencesRepository
import com.example.playersapp_adriansaavedra.data.remote.NetworkResult
import com.example.playersapp_adriansaavedra.data.remote.services.LoginService
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class TokenRefresher @Inject constructor(
    private val loginService: LoginService,
    private val preferencesRepository: PreferencesRepository
) {
    suspend operator fun invoke(): NetworkResult<AuthenticationResponse> {
        return try {
            val refreshToken = preferencesRepository.refreshToken.first()
            if (refreshToken.isNullOrEmpty()) {
                return NetworkResult.Error("No refresh token available")
            }

            val response = loginService.refreshToken(RefreshTokenRequest(refreshToken))

            response.body()?.let { newTokens ->
                preferencesRepository.saveToken(newTokens.accessToken)
                preferencesRepository.saveRefreshToken(newTokens.refreshToken)
                NetworkResult.Success(newTokens)
            } ?: NetworkResult.Error("Failed to refresh token: Empty response body")

        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: "Unknown error occurred while refreshing token")
        }
    }
}

