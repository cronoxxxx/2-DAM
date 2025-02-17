package com.example.playersapp_adriansaavedra.domain.usecases.credential

import com.example.playersapp_adriansaavedra.data.*
import com.example.playersapp_adriansaavedra.data.remote.NetworkResult
import com.example.playersapp_adriansaavedra.data.remote.model.Login
import com.example.playersapp_adriansaavedra.data.remote.utils.AuthenticationResponse
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository,
    private val preferencesRepository: PreferencesRepository
) {
    suspend operator fun invoke(username: String, password: String) : NetworkResult<AuthenticationResponse> {
        val result = loginRepository.login(Login(username, password))
        if (result is NetworkResult.Success) {
            preferencesRepository.saveTokens(result.data.accessToken, result.data.refreshToken)
            preferencesRepository.saveUserId(result.data.userId)
        }
        return result
    }
}
