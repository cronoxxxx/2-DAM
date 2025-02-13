package com.example.consolesapp_adriansaavedra.ui.pantallaConsolaDetalle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.consolesapp_adriansaavedra.data.NetworkResult
import com.example.consolesapp_adriansaavedra.domain.model.Console
import com.example.consolesapp_adriansaavedra.domain.usecases.console.DeleteConsoleUseCase
import com.example.consolesapp_adriansaavedra.domain.usecases.console.GetConsoleUseCase
import com.example.consolesapp_adriansaavedra.domain.usecases.console.UpdateConsoleUseCase
import com.example.consolesapp_adriansaavedra.ui.common.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConsoleDetailViewModel @Inject constructor(
    private val getConsoleUseCase: GetConsoleUseCase,
    private val updateConsoleUseCase: UpdateConsoleUseCase,
    private val deleteConsoleUseCase: DeleteConsoleUseCase
) :
    ViewModel() {
    private val _uiState = MutableStateFlow(ConsoleDetailState())
    val uiState = _uiState.asStateFlow()


    private fun avisoVisto() {
        _uiState.update { it.copy(aviso = null) }
    }

    fun handleEvent(event: ConsoleDetailEvent) {
        when (event) {
            is ConsoleDetailEvent.GetConsole -> getConsole(event.consoleId)
            is ConsoleDetailEvent.UpdateConsole -> updateConsole(event.console)
            is ConsoleDetailEvent.DeleteConsole -> deleteConsole(event.consoleId)
            is ConsoleDetailEvent.AvisoVisto -> avisoVisto()
            is ConsoleDetailEvent.OnNameChange -> updateName(event.value)
            is ConsoleDetailEvent.OnModelChange -> updateModel(event.value)
            is ConsoleDetailEvent.OnPriceChange -> updatePrice(event.value)
        }
    }


    private fun getConsole(id: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            when (val console = getConsoleUseCase(id)) {
                is NetworkResult.Success -> {
                    _uiState.update { it.copy(console = console.data, isLoading = false) }
                }

                is NetworkResult.Error -> {
                    _uiState.update {
                        it.copy(
                            aviso = UiEvent.ShowSnackbar(console.message),
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    private fun updateConsole(console: Console) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            when (val result = updateConsoleUseCase(console)) {
                is NetworkResult.Success -> {
                    _uiState.update {
                        it.copy(
                            aviso = UiEvent.Navigate,
                            isLoading = false
                        )
                    }
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

    private fun deleteConsole(id: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            when (val result = deleteConsoleUseCase(id)) {
                is NetworkResult.Success -> {
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

    private fun updateName(value: String) {
        _uiState.update { currentState ->
            currentState.copy(
                console = currentState.console?.copy(nombre = value)
            )
        }
    }

    private fun updateModel(value: String) {
        _uiState.update { currentState ->
            currentState.copy(
                console = currentState.console?.copy(modelo = value)
            )
        }
    }

    private fun updatePrice(value: String) {
        val newPrice = value.toDoubleOrNull() ?: return
        _uiState.update { currentState ->
            currentState.copy(
                console = currentState.console?.copy(precio = newPrice)
            )
        }
    }


}