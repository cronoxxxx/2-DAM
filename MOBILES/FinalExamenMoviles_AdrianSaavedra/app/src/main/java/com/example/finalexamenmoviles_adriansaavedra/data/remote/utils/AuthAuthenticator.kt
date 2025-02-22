package com.example.playersapp_adriansaavedra.data.remote.utils


import com.example.finalexamenmoviles_adriansaavedra.data.PreferencesRepository
import com.example.finalexamenmoviles_adriansaavedra.data.remote.services.LoginService
import com.example.finalexamenmoviles_adriansaavedra.data.remote.utils.AuthenticationResponse
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