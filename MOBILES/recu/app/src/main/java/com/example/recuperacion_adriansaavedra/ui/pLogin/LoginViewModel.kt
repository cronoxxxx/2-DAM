package com.example.recuperacion_adriansaavedra.ui.pLogin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalexamenmoviles_adriansaavedra.ui.common.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {
    // Aquí puedes inyectar los casos de uso necesarios para el login
    // private val loginUseCase: LoginUseCase,
    // private val preferenceRepository: PreferencesRepository
    
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
        // lógica de login durante el examen

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            
            // Simular una llamada de red
            delay(1000)
            
            // DURANTE EL EXAMEN: Reemplazar esto con la llamada real al caso de uso
            // when (val result = loginUseCase.invoke(_uiState.value.username, _uiState.value.password)) {
            
            // Simulamos un login exitoso si el usuario y contraseña son "admin"
            if (_uiState.value.username == "admin" && _uiState.value.password == "admin") {
                // DURANTE EL EXAMEN: Guardar el token real
                // preferenceRepository.saveToken(result.data.accessToken)
                _uiState.update { it.copy(aviso = UiEvent.Navigate, isLoading = false) }
            } else {
                _uiState.update {
                    it.copy(
                        aviso = UiEvent.ShowSnackbar("Usuario o contraseña incorrectos"),
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