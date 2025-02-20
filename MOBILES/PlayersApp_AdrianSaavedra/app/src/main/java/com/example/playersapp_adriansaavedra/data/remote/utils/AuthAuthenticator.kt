package com.example.playersapp_adriansaavedra.data.remote.utils

import com.example.playersapp_adriansaavedra.data.PreferencesRepository
import com.example.playersapp_adriansaavedra.data.remote.services.LoginService
import com.example.playersapp_adriansaavedra.ui.Constantes
import dagger.Lazy
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject


class AuthAuthenticator @Inject constructor(
    private val preferencesRepository: PreferencesRepository,
    private val loginService: Lazy<LoginService>
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        return runBlocking {
            val refreshToken = preferencesRepository.refreshToken.first()
            val newToken = getNewToken(refreshToken)

            if (newToken == null) {
                preferencesRepository.clearTokens()
                null
            } else {
                preferencesRepository.saveTokens(newToken.accessToken, newToken.refreshToken)
                response.request.newBuilder()
                    .header(
                        Constantes.AUTHORIZATION,
                        Constantes.BEARER_PREFIX + newToken.accessToken
                    )
                    .build()
            }
        }
    }

    private suspend fun getNewToken(refreshToken: String?): AuthenticationResponse? {
        return try {
            val response = loginService.get().refreshToken(RefreshTokenRequest(refreshToken ?: ""))
            if (response.isSuccessful) response.body() else null
        } catch (e: Exception) {
            null
        }
    }
}