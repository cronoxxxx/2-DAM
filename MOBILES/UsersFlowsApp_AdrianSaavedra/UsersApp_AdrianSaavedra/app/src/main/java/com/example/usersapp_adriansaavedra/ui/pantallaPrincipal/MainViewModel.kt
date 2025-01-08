package com.example.usersapp_adriansaavedra.ui.pantallaPrincipal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.usersapp_adriansaavedra.R
import com.example.usersapp_adriansaavedra.data.remote.NetworkResult
import com.example.usersapp_adriansaavedra.domain.usecases.usuarios.GetUsersUseCase
import com.example.usersapp_adriansaavedra.ui.common.StringProvider
import com.example.usersapp_adriansaavedra.ui.common.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase,
    private val stringProvider: StringProvider
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
            when (val result = getUsersUseCase()) {
                is NetworkResult.Success -> {
                    _uiState.update { currentState ->
                        currentState.copy(users = result.data)
                    }
                }
                is NetworkResult.Error -> {
                    _uiState.update { currentState ->
                        currentState.copy(aviso = UiEvent.ShowSnackbar(stringProvider.getString(R.string.error_obteniendo_usuarios)))
                    }
                }
            }
        }
    }

    private fun getFiltrados(filtro: String) {
        viewModelScope.launch {
            when (val result = getUsersUseCase()) {
                is NetworkResult.Error -> {
                    _uiState.update { currentState ->
                        currentState.copy(aviso = UiEvent.ShowSnackbar(stringProvider.getString(R.string.error_obteniendo_usuarios)))
                    }
                }
                is NetworkResult.Success -> {
                    _uiState.update { currentState ->
                        currentState.copy(users = result.data.filter { it.name.contains(filtro,ignoreCase = true) })
                    }
                }
            }
        }
    }



}