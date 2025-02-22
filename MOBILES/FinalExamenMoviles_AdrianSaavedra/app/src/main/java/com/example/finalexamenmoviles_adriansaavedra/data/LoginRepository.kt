package com.example.finalexamenmoviles_adriansaavedra.data

import com.example.finalexamenmoviles_adriansaavedra.data.remote.NetworkResult
import com.example.finalexamenmoviles_adriansaavedra.data.remote.datasource.BaseApiResponse
import com.example.finalexamenmoviles_adriansaavedra.data.remote.services.LoginService
import com.example.finalexamenmoviles_adriansaavedra.di.IoDispatcher
import com.example.playersapp_adriansaavedra.data.remote.model.LoginUser
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val loginService: LoginService,
    private val preferencesRepository: PreferencesRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : BaseApiResponse() {
    suspend fun login(username: String, password: String) = withContext(dispatcher) {
        val result = safeApiCall { loginService.login(LoginUser(username, password)) }
        if (result is NetworkResult.Success) {
            preferencesRepository.saveTokens(result.data.accessToken)
        }
        result
    }
}