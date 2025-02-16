package com.example.playersapp_adriansaavedra.ui.pantallaFavoritos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playersapp_adriansaavedra.data.PreferencesRepository
import com.example.playersapp_adriansaavedra.data.remote.NetworkResult
import com.example.playersapp_adriansaavedra.domain.usecases.favorites.GetFavoritePlayersUseCase
import com.example.playersapp_adriansaavedra.ui.common.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoritePlayersUseCase: GetFavoritePlayersUseCase,
    preferencesRepository: PreferencesRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(FavoritesState())
    val uiState = _uiState.asStateFlow()
    val userId = preferencesRepository.userId
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )

    private fun avisoVisto() {
        _uiState.update { it.copy(aviso = null) }
    }

    fun handleEvent(event: FavoritesEvent) {
        when (event) {
            is FavoritesEvent.OnAvisoVisto -> avisoVisto()
            is FavoritesEvent.OnGetFavoritePlayers -> getFavoritePlayers(event.id)
            is FavoritesEvent.OnPlayerClick -> selectPlayer(event.playerId)
        }
    }

    private fun getFavoritePlayers(id: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            when (val result = getFavoritePlayersUseCase.invoke(id)) {
                is NetworkResult.Success -> {
                    _uiState.update { it.copy(favoritePlayers = result.data, isLoading = false) }
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

    private fun selectPlayer(playerId: Int) {
        _uiState.update {
            it.copy(
                selectedPlayerId = playerId,
                aviso = UiEvent.Navigate
            )
        }
    }
}