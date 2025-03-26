package com.example.finalexamenmoviles_adriansaavedra.ui.pantallaLogin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalexamenmoviles_adriansaavedra.data.PreferencesRepository
import com.example.finalexamenmoviles_adriansaavedra.data.remote.NetworkResult
import com.example.finalexamenmoviles_adriansaavedra.domain.usecases.session.LoginUseCase
import com.example.finalexamenmoviles_adriansaavedra.ui.common.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val preferenceRepository: PreferencesRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginState())
    val state = _uiState.asStateFlow()

    fun handleEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnAvisoVisto -> avisoVisto()
            is LoginEvent.OnLoginClick -> login()
            is LoginEvent.OnUsernameChange -> updateUsername(event.username)
            is LoginEvent.OnPasswordChange -> updatePassword(event.password)
        }
    }

    private fun avisoVisto() {
        _uiState.update { currentState -> currentState.copy(aviso = null) }
    }

    private fun login() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            when (val result = loginUseCase.invoke(_uiState.value.username, _uiState.value.password)) {
                is NetworkResult.Success -> {
                    preferenceRepository.saveTokens(result.data.accessToken)
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

    private fun updateUsername(value: String) {
        _uiState.update { it.copy(username = value) }
    }

    private fun updatePassword(value: String) {
        _uiState.update { it.copy(password = value) }
    }
}
