package com.example.playersapp_adriansaavedra.ui.pantallaLogin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playersapp_adriansaavedra.data.PreferencesRepository
import com.example.playersapp_adriansaavedra.data.remote.NetworkResult
import com.example.playersapp_adriansaavedra.domain.usecases.credential.LoginUseCase
import com.example.playersapp_adriansaavedra.domain.usecases.credential.RegisterUseCase
import com.example.playersapp_adriansaavedra.ui.common.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
    private val preferencesRepository: PreferencesRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginState())
    val state = _uiState.asStateFlow()

    fun handleEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnAvisoVisto -> avisoVisto()
            is LoginEvent.OnLoginClick -> login(event.username, event.password)
            is LoginEvent.OnRegisterClick -> register(event.username, event.password, event.email)
            is LoginEvent.OnUsernameLoginChange -> updateUsernameLogin(event.username)
            is LoginEvent.OnPasswordLoginChange -> updatePasswordLogin(event.password)
            is LoginEvent.OnEmailRegisterChange -> updateEmailRegister(event.email)
            is LoginEvent.OnPasswordRegisterChange -> updatePasswordRegister(event.password)
            is LoginEvent.OnUsernameRegisterChange -> updateUsernameRegister(event.username)
        }
    }
    private fun avisoVisto() {
        _uiState.update { currentState -> currentState.copy(aviso = null) }
    }

    private fun login(username: String, password: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            when (val result = loginUseCase.invoke(username, password)) {
                is NetworkResult.Success -> {
                    _uiState.update { it.copy(aviso = UiEvent.Navigate, isLoading = false) }
                }

                is NetworkResult.Error -> {
                    _uiState.update {
                        it.copy(
                            aviso = UiEvent.ShowSnackbar(result.message),
                            isLoading = false
                        )
                    }
                }
            }

        }
    }

    private fun register(username: String, password: String, email: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            when (val result = registerUseCase.invoke(username, password, email)) {
                is NetworkResult.Success -> {
                    _uiState.update { it.copy(aviso = UiEvent.Navigate, isLoading = false) }
                }

                is NetworkResult.Error -> {
                    _uiState.update {
                        it.copy(
                            aviso = UiEvent.ShowSnackbar(result.message),
                            isLoading = false
                        )
                    }
                }
            }

        }
    }

    private fun updateUsernameLogin(value: String) {
        _uiState.update { it.copy(login = it.login.copy(username = value)) }
    }

    private fun updatePasswordLogin(value: String) {
        _uiState.update { it.copy(login = it.login.copy(password = value)) }
    }
    private fun updateEmailRegister(value: String) {
        _uiState.update { it.copy(register = it.register.copy(email = value)) }
    }
    private fun updatePasswordRegister(value: String) {
        _uiState.update { it.copy(register = it.register.copy(password = value)) }
    }
    private fun updateUsernameRegister(value: String) {
        _uiState.update { it.copy(register = it.register.copy(username = value)) }
    }
}