package com.example.consolesapp_adriansaavedra.ui.pantallaJugadorDetalle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.consolesapp_adriansaavedra.data.NetworkResult
import com.example.consolesapp_adriansaavedra.domain.usecases.player.GetPlayerConsolesUseCase
import com.example.consolesapp_adriansaavedra.ui.common.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerDetailViewModel @Inject constructor(
    private val getPlayerUseCase: GetPlayerConsolesUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(PlayerDetailState())
    val uiState = _uiState.asStateFlow()

    fun handleEvent(event: PlayerDetailEvent) {
        when (event) {
            is PlayerDetailEvent.GetPlayerConsoles -> getPlayer(event.id)
            is PlayerDetailEvent.AvisoVisto -> avisoVisto()
        }
    }

    private fun getPlayer(id: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            when (val result = getPlayerUseCase(id)) {
                is NetworkResult.Success -> {
                    _uiState.update { it.copy(player = result.data, isLoading = false) }
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

    private fun avisoVisto() {
        _uiState.update { it.copy(aviso = null) }
    }
}