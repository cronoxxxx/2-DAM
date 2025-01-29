package com.example.hospitalapp_adriansaavedra.ui.pantallaLogin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hospitalapp_adriansaavedra.data.remote.NetworkResult
import com.example.hospitalapp_adriansaavedra.domain.usecases.records.GetPatientRecordsUseCase
import com.example.hospitalapp_adriansaavedra.ui.common.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getPatientRecordsUseCase: GetPatientRecordsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginState())
    val state = _uiState.asStateFlow()

    fun handleEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnLoginClick -> login(event.patientId)
            is LoginEvent.AvisoVisto -> avisoVisto()
            is LoginEvent.OnIdLoginChange -> updateIdLogin(event.value)
        }
    }

    private fun login(patientId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val result = getPatientRecordsUseCase(patientId)
            result.collect { networkResult ->
                when (networkResult) {
                    is NetworkResult.Success -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                aviso = UiEvent.Navigate
                            )
                        }
                    }

                    is NetworkResult.Error -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                aviso = UiEvent.ShowSnackbar(networkResult.message)
                            )
                        }
                    }

                    is NetworkResult.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }
                }
            }
        }
    }

    private fun avisoVisto() {
        _uiState.update { currentState -> currentState.copy(aviso = null) }
    }

    private fun updateIdLogin(value: String) {
        _uiState.update { it.copy(idLogin = value) }
    }
}