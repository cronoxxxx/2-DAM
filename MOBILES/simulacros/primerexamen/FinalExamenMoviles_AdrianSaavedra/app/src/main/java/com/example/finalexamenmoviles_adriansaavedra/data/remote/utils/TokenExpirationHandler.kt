package com.example.finalexamenmoviles_adriansaavedra.data.remote.utils

import com.example.finalexamenmoviles_adriansaavedra.data.PreferencesRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenExpirationHandler @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) {
    private val _tokenExpired = MutableSharedFlow<Unit>()
    val tokenExpired = _tokenExpired.asSharedFlow()

    suspend fun onTokenExpired() {
        preferencesRepository.clearTokens()
        _tokenExpired.emit(Unit)
    }
}