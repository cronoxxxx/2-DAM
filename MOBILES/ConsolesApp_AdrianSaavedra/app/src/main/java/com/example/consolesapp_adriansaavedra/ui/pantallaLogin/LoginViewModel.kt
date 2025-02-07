package com.example.consolesapp_adriansaavedra.ui.pantallaLogin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.consolesapp_adriansaavedra.data.NetworkResult
import com.example.consolesapp_adriansaavedra.data.PlayerConsoleRepository
import com.example.consolesapp_adriansaavedra.data.PreferencesRepository
import com.example.consolesapp_adriansaavedra.di.IoDispatcher
import com.example.consolesapp_adriansaavedra.domain.model.Player
import com.example.consolesapp_adriansaavedra.domain.usecases.player.GetPlayersUseCase
import com.example.consolesapp_adriansaavedra.domain.usecases.player.RegisterPlayerUseCase
import com.example.consolesapp_adriansaavedra.ui.common.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getPlayersUseCase: GetPlayersUseCase,
    private val registerPlayerUseCase: RegisterPlayerUseCase,
    private val preferencesRepository: PreferencesRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginState())
    val state = _uiState.asStateFlow()

    fun handleEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnLoginClick -> login(event.player)
            is LoginEvent.OnRegisterClick -> register(event.player)
            is LoginEvent.AvisoVisto -> avisoVisto()
            is LoginEvent.OnUsernameChange -> updateUsername(event.value)
            is LoginEvent.OnPasswordChange -> updatePassword(event.value)
        }
    }

    private fun avisoVisto() {
        _uiState.update { currentState -> currentState.copy(aviso = null) }
    }

    private fun login(player: Player) {
        viewModelScope.launch {

            when (val result = getPlayersUseCase()) {
                is NetworkResult.Success -> {
                    val obtained = result.data.find { it.username == player.username && it.password == player.password }
                    if (obtained != null) {
                        preferencesRepository.saveUserId(obtained.jugadorId)
                        _uiState.update { currentState ->
                            currentState.copy(
                                idLogin = obtained.jugadorId.toString(),
                                aviso = UiEvent.Navigate,
                                isLoading = false
                            )
                        }
                    } else {
                        _uiState.update { currentState ->
                            currentState.copy(
                                aviso = UiEvent.ShowSnackbar("Login error"),
                                isLoading = false
                            )
                        }
                    }
                }
                is NetworkResult.Error -> {
                    _uiState.update { currentState ->
                        currentState.copy(
                            aviso = UiEvent.ShowSnackbar(result.message),
                            isLoading = false
                        )
                    }
                }
                is NetworkResult.Loading -> {
                    _uiState.update { it.copy(isLoading = true) }
                }
            }
        }
    }

    private fun register(player: Player) {
        viewModelScope.launch {
            when (val playersResult = getPlayersUseCase()) {
                is NetworkResult.Success -> {
                    val obtained = playersResult.data.find { it.username == player.username }
                    if (obtained == null) {
                        when (val registerResult = registerPlayerUseCase(player)) {
                            is NetworkResult.Success -> {
                                _uiState.update { currentState ->
                                    currentState.copy(
                                        aviso = UiEvent.ShowSnackbar("Registration successful"),
                                        isLoading = false
                                    )
                                }
                            }
                            is NetworkResult.Error -> {
                                _uiState.update { currentState ->
                                    currentState.copy(
                                        aviso = UiEvent.ShowSnackbar("Registration error: ${registerResult.message}"),
                                        isLoading = false
                                    )
                                }
                            }
                            is NetworkResult.Loading -> {
                                // This state is handled by setting isLoading to true at the start of the function
                            }
                        }
                    } else {
                        _uiState.update { currentState ->
                            currentState.copy(
                                aviso = UiEvent.ShowSnackbar("User already exists"),
                                isLoading = false
                            )
                        }
                    }
                }
                is NetworkResult.Error -> {
                    _uiState.update { currentState ->
                        currentState.copy(
                            aviso = UiEvent.ShowSnackbar("Error checking existing users: ${playersResult.message}"),
                            isLoading = false
                        )
                    }
                }
                is NetworkResult.Loading -> {
                    _uiState.update { it.copy(isLoading = true) }
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