package com.example.usersapp_adriansaavedra.ui.pantallaPrincipal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.usersapp_adriansaavedra.data.remote.NetworkResult
import com.example.usersapp_adriansaavedra.domain.usecases.usuarios.GetUsersUseCase
import com.example.usersapp_adriansaavedra.ui.common.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

//inyectar dispatcher luego
@HiltViewModel
class MainViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainState())
    val uiState = _uiState.asStateFlow()

    init {
        getUsers()
    }

    fun handleEvent(event: MainEvent) {
        when (event) {
            is MainEvent.GetUsers -> getUsers()
            is MainEvent.AvisoVisto -> avisoVisto()
            is MainEvent.GetUsersFiltrados -> getFiltrados(event.filtro)
        }
    }

    private fun avisoVisto() {
        _uiState.update { currentState -> currentState.copy(aviso = null) }
    }

    private fun getUsers() {
        viewModelScope.launch {
            val result = getUsersUseCase.getUsersFromCache()
            result.collect { collected ->
                when (collected) {
                    is NetworkResult.Success -> {
                        _uiState.update { currentState ->
                            currentState.copy(
                                users = collected.data,
                                isLoading = false
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
                                    collected.message
                                ),
                                isLoading = false
                            )
                        }
                    }
                }

            }
        }
    }

    private fun getFiltrados(filtro: String) {
        viewModelScope.launch {
            val result = getUsersUseCase.getUsersFromCache()
            result.collect { collected ->
                when (collected) {
                    is NetworkResult.Success -> {
                        _uiState.update { currentState ->
                            currentState.copy(
                                users = collected.data.filter {
                                    it.name.startsWith(
                                        filtro,
                                        ignoreCase = true
                                    )
                                },
                                isLoading = false
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
                                    collected.message
                                ),
                                isLoading = false
                            )
                        }
                    }
                }
            }

        }
    }
}