package com.example.playersapp_adriansaavedra.domain.usecases.credential

import com.example.playersapp_adriansaavedra.data.PreferencesRepository
import com.example.playersapp_adriansaavedra.data.remote.NetworkResult
import com.example.playersapp_adriansaavedra.data.remote.services.LoginService
import com.example.playersapp_adriansaavedra.data.remote.utils.AuthenticationResponse
import com.example.playersapp_adriansaavedra.data.remote.utils.RefreshTokenRequest
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class RefreshTokenUseCase @Inject constructor(
    private val loginService: LoginService,
    private val preferencesRepository: PreferencesRepository
) {
    suspend operator fun invoke(): NetworkResult<AuthenticationResponse> {
        val refreshToken = preferencesRepository.refreshToken.first()
        return try {
            val response = loginService.refreshToken(RefreshTokenRequest(refreshToken ?: ""))
            if (response.isSuccessful && response.body() != null) {
                val newTokens = response.body()!!
                preferencesRepository.saveToken(newTokens.accessToken)
                preferencesRepository.saveRefreshToken(newTokens.refreshToken)
                NetworkResult.Success(newTokens)
            } else {
                NetworkResult.Error("Failed to refresh token")
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: "Unknown error")
        }
    }
}

