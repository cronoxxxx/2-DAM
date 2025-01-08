package com.example.usersapp_adriansaavedra.ui.pantallaAgregar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.usersapp_adriansaavedra.R
import com.example.usersapp_adriansaavedra.data.remote.NetworkResult
import com.example.usersapp_adriansaavedra.domain.modelo.User
import com.example.usersapp_adriansaavedra.domain.usecases.usuarios.AddUserUseCase
import com.example.usersapp_adriansaavedra.ui.common.StringProvider
import com.example.usersapp_adriansaavedra.ui.common.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddUsuarioViewModel @Inject constructor(
    private val stringProvider: StringProvider,
    private val addUserUseCase: AddUserUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(AddUsuarioState())
    val uiState = _uiState.asStateFlow()

    fun handleEvent(event: AddUsuarioEvent) {
        when (event) {
            is AddUsuarioEvent.AddUser -> addUser(event.user)
            AddUsuarioEvent.AvisoVisto -> avisoMostrado()
        }
    }

    private fun addUser(user: User) {
            viewModelScope.launch {
                when (val result = addUserUseCase(user)) {
                    is NetworkResult.Success -> {
                        _uiState.update {
                            currentState -> currentState.copy(
                                aviso = UiEvent.ShowSnackbar(stringProvider.getString(R.string.usuario_agregado_exitosamente))
                            )
                        }
                        _uiState.update { currentState -> currentState.copy(aviso = UiEvent.PopBackStack) }
                    }
                    is NetworkResult.Error -> {
                        _uiState.update {
                            currentState -> currentState.copy(
                                aviso = UiEvent.ShowSnackbar(result.message)
                            )
                        }
                    }
                }

        }
    }
    private fun avisoMostrado() {
        _uiState.update { currentState -> currentState.copy(aviso = null) }
    }
}