package com.example.consolesapp_adriansaavedra.ui.pantallaAgregarConsola

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.consolesapp_adriansaavedra.data.NetworkResult
import com.example.consolesapp_adriansaavedra.data.PreferencesRepository
import com.example.consolesapp_adriansaavedra.domain.model.Console
import com.example.consolesapp_adriansaavedra.domain.usecases.console.AddConsoleUseCase
import com.example.consolesapp_adriansaavedra.ui.common.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddConsoleViewModel @Inject constructor(
    private val addConsoleUseCase: AddConsoleUseCase,
     preferencesRepository: PreferencesRepository
) :
    ViewModel() {
    private val _uiState = MutableStateFlow(AddConsoleState())
    val uiState = _uiState.asStateFlow()
    val userId = preferencesRepository.userId
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )

    fun handleEvent(event: AddConsoleEvent) {
        when (event) {
            is AddConsoleEvent.AddConsole -> addConsole(event.userId, event.console)
            is AddConsoleEvent.OnNameChange -> updateName(event.value)
            is AddConsoleEvent.OnModelChange -> updateModel(event.value)
            is AddConsoleEvent.OnPriceChange -> updatePrice(event.value)
            is AddConsoleEvent.UpdateUserId -> updateUserId(event.userId)
            is AddConsoleEvent.AvisoVisto -> avisoMostrado()
        }
    }

    private fun updateUserId(userId: Int) {
        _uiState.update { it.copy(userId = userId) }
    }

    private fun avisoMostrado() {
        _uiState.value = _uiState.value.copy(aviso = null)
    }

    private fun addConsole(userId: Int, consola: Console) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            when (val result = addConsoleUseCase(userId, consola)) {
                is NetworkResult.Success -> {
                    _uiState.value =
                        _uiState.value.copy(aviso = UiEvent.Navigate, isLoading = false)
                }

                is NetworkResult.Error -> {
                    _uiState.value = _uiState.value.copy(
                        aviso = UiEvent.ShowSnackbar(result.message),
                        isLoading = false
                    )
                }
            }

        }
    }

    private fun updateName(value: String) {
        _uiState.update { it.copy(console = it.console.copy(nombre = value)) }
    }

    private fun updateModel(value: String) {
        _uiState.update { it.copy(console = it.console.copy(modelo = value)) }
    }

    private fun updatePrice(value: String) {
        val newPrice = value.toDoubleOrNull() ?: return
        _uiState.update { it.copy(console = it.console.copy(precio = newPrice)) }
    }

}