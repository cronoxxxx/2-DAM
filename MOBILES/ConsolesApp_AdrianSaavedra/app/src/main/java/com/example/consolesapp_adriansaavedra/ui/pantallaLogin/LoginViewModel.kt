package com.example.consolesapp_adriansaavedra.ui.pantallaLogin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    @IoDispatcher val dispatcher: CoroutineDispatcher
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
        viewModelScope.launch(dispatcher) {
            _uiState.update { it.copy(isLoading = true) }
            val cachedUsers = getPlayersUseCase
            val obtained = cachedUsers.invoke()
                .find { it.username == player.username && it.password == player.password }
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
    }

    private fun register(player: Player) {
        viewModelScope.launch(dispatcher) {
            _uiState.update { it.copy(isLoading = true) }
            val cachedUsers = getPlayersUseCase
            val obtained = cachedUsers.invoke()
                .find { it.username == player.username && it.password == player.password }
            if (obtained == null) {
                registerPlayerUseCase(player)
                _uiState.update { currentState ->
                    currentState.copy(
                        aviso = UiEvent.ShowSnackbar("Registration successful"),
                        isLoading = false
                    )
                }
            } else {
                _uiState.update { currentState ->
                    currentState.copy(
                        aviso = UiEvent.ShowSnackbar("Register error"),
                        isLoading = false
                    )
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