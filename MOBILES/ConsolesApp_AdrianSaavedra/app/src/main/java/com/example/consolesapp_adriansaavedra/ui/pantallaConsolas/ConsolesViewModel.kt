package com.example.consolesapp_adriansaavedra.ui.pantallaConsolas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.consolesapp_adriansaavedra.data.NetworkResult
import com.example.consolesapp_adriansaavedra.data.PreferencesRepository
import com.example.consolesapp_adriansaavedra.domain.usecases.player.GetPlayerConsolesUseCase
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
class ConsolesViewModel @Inject constructor(
    private val getPlayerConsolesUseCase: GetPlayerConsolesUseCase,
    private val preferencesRepository: PreferencesRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ConsolesState())
    val uiState = _uiState.asStateFlow()
    val userId = preferencesRepository.userId
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )

    fun handleEvent(event: ConsolesEvent) {
        when (event) {
            is ConsolesEvent.OnConsolesClick -> selectConsole(event.consoleId)
            is ConsolesEvent.AvisoVisto -> avisoVisto()
            is ConsolesEvent.LoadConsoles -> getPlayerConsoles(event.userId)
        }
    }

    private fun getPlayerConsoles(id: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            when (val player = getPlayerConsolesUseCase(id)) {
                is NetworkResult.Error -> _uiState.update {
                    it.copy(
                        aviso = UiEvent.ShowSnackbar(
                            player.message
                        )
                    )
                }

                is NetworkResult.Success -> {
                    _uiState.update {
                        it.copy(
                            playerConsoles = player.data.consolasList,
                            isLoading = false
                        )
                    }
                }
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