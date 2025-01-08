package com.example.usersapp_adriansaavedra.ui.pantallaUsuario

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.usersapp_adriansaavedra.R
import com.example.usersapp_adriansaavedra.data.remote.NetworkResult
import com.example.usersapp_adriansaavedra.domain.modelo.User
import com.example.usersapp_adriansaavedra.domain.usecases.usuarios.DeleteUserUseCase
import com.example.usersapp_adriansaavedra.domain.usecases.usuarios.GetUserUseCase
import com.example.usersapp_adriansaavedra.domain.usecases.usuarios.UpdateUserUseCase
import com.example.usersapp_adriansaavedra.ui.common.StringProvider
import com.example.usersapp_adriansaavedra.ui.common.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetalleViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    private val stringProvider: StringProvider
) : ViewModel() {
    private val _uiState = MutableStateFlow(DetalleState())
    val uiState = _uiState.asStateFlow()
    fun handleEvent(event: DetalleEvent) {
        when (event) {
            is DetalleEvent.GetUser -> getUser(event.id)
            is DetalleEvent.AvisoVisto -> avisoMostrado()
            is DetalleEvent.UpdateUser -> updateUser(event.user)
            is DetalleEvent.DeleteUser -> deleteUser(event.id)
        }
    }

    private fun getUser(id: Int) {
        viewModelScope.launch {
            getUserUseCase.invoke(id).collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        _uiState.update { currentState -> currentState.copy(user = result.data) }
                    }

                    is NetworkResult.Loading -> {
                        _uiState.update { currentState -> currentState.copy(isLoading = true) }
                    }

                    is NetworkResult.Error -> {
                        _uiState.update { currentState ->
                            currentState.copy(
                                aviso = UiEvent.ShowSnackbar(
                                    result.message
                                )
                            )
                        }

                    }
                }
            }
        }
    }

    private fun updateUser(received: User) {
        viewModelScope.launch {
            updateUserUseCase.invoke(received).collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        _uiState.update { currentState ->
                            currentState.copy(
                                user = received,
                                aviso = UiEvent.ShowSnackbar(stringProvider.getString(R.string.usuario_actualizado_exitosamente))
                            )
                        }
                        _uiState.update { currentState ->
                            currentState.copy(
                                aviso = UiEvent.PopBackStack
                            )
                        }
                    }
                    is NetworkResult.Loading -> {
                        _uiState.update { currentState -> currentState.copy(isLoading = true) }
                    }

                    is NetworkResult.Error -> {
                        _uiState.update { currentState ->
                            currentState.copy(
                                aviso = UiEvent.ShowSnackbar(
                                    result.message
                                )
                            )
                        }

                    }


                }

            }
        }
    }

    private fun deleteUser(id: Int) {
        viewModelScope.launch {
            deleteUserUseCase(id).collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        _uiState.update { currentState ->
                            currentState.copy(
                                aviso = UiEvent.ShowSnackbar(stringProvider.getString(R.string.usuario_eliminado_exitosamente))
                            )
                        }
                        _uiState.update { currentState ->
                            currentState.copy(
                                aviso = UiEvent.PopBackStack
                            )
                        }
                    }

                    is NetworkResult.Loading -> {
                        _uiState.update { currentState -> currentState.copy(isLoading = true) }
                    }

                    is NetworkResult.Error -> {
                        _uiState.update { currentState ->
                            currentState.copy(
                                aviso = UiEvent.ShowSnackbar(
                                    result.message
                                )
                            )
                        }
                    }
                }

            }
        }
    }

    private fun avisoMostrado() {
        _uiState.update { currentState -> currentState.copy(aviso = null) }
    }

}