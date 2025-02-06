package com.example.consolesapp_adriansaavedra.ui.pantallaConsolas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.consolesapp_adriansaavedra.data.PreferencesRepository
import com.example.consolesapp_adriansaavedra.di.IoDispatcher
import com.example.consolesapp_adriansaavedra.domain.usecases.player.GetPlayerConsolesUseCase
import com.example.consolesapp_adriansaavedra.ui.common.PreferencesViewModel
import com.example.consolesapp_adriansaavedra.ui.common.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConsoleViewModel @Inject constructor(
    private val getPlayerConsolesUseCase: GetPlayerConsolesUseCase,
    private val preferencesRepository: PreferencesRepository,
    @IoDispatcher val dispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _uiState = MutableStateFlow(ConsoleState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            preferencesRepository.userId.collect { id ->
                if (id > 0) {
                    getPlayerConsoles(id)
                }
            }
        }
    }

    fun handleEvent(event: ConsoleEvent) {
        when (event) {
            is ConsoleEvent.OnConsoleClick -> selectConsole(event.consoleId)
            is ConsoleEvent.AvisoVisto -> avisoVisto()
        }
    }

    private fun getPlayerConsoles(id: Int) {
        viewModelScope.launch(dispatcher) {
            _uiState.update { it.copy(isLoading = true) }
                val player = getPlayerConsolesUseCase(id)
                _uiState.update {
                    it.copy(
                        playerConsoles = player.consolasList,
                        isLoading = false
                    )
                }

        }
    }

    private fun selectConsole(consoleId: Int) {
        _uiState.update {
            it.copy(
                selectedConsoleId = consoleId,
                aviso = UiEvent.Navigate
            )
        }
    }

    private fun avisoVisto() {
        _uiState.update { it.copy(aviso = null) }
    }
}