package com.example.consolesapp_adriansaavedra.ui.pantallaJugadores

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.consolesapp_adriansaavedra.data.NetworkResult
import com.example.consolesapp_adriansaavedra.domain.usecases.player.GetPlayersUseCase
import com.example.consolesapp_adriansaavedra.ui.common.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayersViewModel @Inject constructor(
    private val getPlayersUseCase: GetPlayersUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(PlayersState())
    val uiState = _uiState.asStateFlow()


    fun handleEvent(event: PlayersEvent) {
        when (event) {
            is PlayersEvent.GetPlayers -> getPlayers()
            is PlayersEvent.SelectConsole -> selectConsole(event.playerId)
            is PlayersEvent.AvisoVisto -> avisoVisto()

        }
    }

    private fun getPlayers() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }
            when (val result = getPlayersUseCase()) {
                is NetworkResult.Success -> {
                    _uiState.update {
                        it.copy(
                            players = result.data,
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

    private fun selectConsole(playerId: Int) {
        _uiState.update {
            it.copy(
                selectedPlayerId = playerId,
                aviso = UiEvent.Navigate
            )
        }
    }

    private fun avisoVisto() {
        _uiState.update { it.copy(aviso = null) }
    }
}