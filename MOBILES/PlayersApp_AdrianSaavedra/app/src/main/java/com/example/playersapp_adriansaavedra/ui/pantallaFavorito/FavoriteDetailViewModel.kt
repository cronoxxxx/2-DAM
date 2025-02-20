package com.example.playersapp_adriansaavedra.ui.pantallaFavorito

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playersapp_adriansaavedra.data.PreferencesRepository
import com.example.playersapp_adriansaavedra.data.remote.NetworkResult
import com.example.playersapp_adriansaavedra.domain.usecases.favorites.DeleteFavoritePlayerUseCase
import com.example.playersapp_adriansaavedra.domain.usecases.favorites.GetFavoritePlayerUseCase
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
class FavoriteDetailViewModel @Inject constructor(
    private val getFavoritePlayerUseCase: GetFavoritePlayerUseCase,
    private val deleteFavoritePlayerUseCase: DeleteFavoritePlayerUseCase,
    preferencesRepository: PreferencesRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(FavoriteDetailState())
    val uiState = _uiState.asStateFlow()

    val userId = preferencesRepository.userId
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )

    fun handleEvent(event: FavoriteDetailEvent) {
        when (event) {
            is FavoriteDetailEvent.AvisoVisto -> avisoVisto()
            is FavoriteDetailEvent.GetFavPlayer -> getFavPlayer(event.credentialId, event.playerId)
            is FavoriteDetailEvent.DeleteFavPlayer -> deleteFavPlayer(
                event.credentialId,
                event.playerId
            )

            is FavoriteDetailEvent.UpdateUserId -> updateUserId(event.userId)
        }
    }

    private fun updateUserId(userId: Int) {
        _uiState.update { it.copy(userId = userId) }
    }

    private fun avisoVisto() {
        _uiState.update { it.copy(aviso = null) }
    }


    private fun getFavPlayer(credentialId: Int, playerId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            when (val player = getFavoritePlayerUseCase.invoke(credentialId, playerId)) {
                is NetworkResult.Success -> {
                    _uiState.update { it.copy(player = player.data, isLoading = false) }
                }

                is NetworkResult.Error -> {
                    _uiState.update {
                        it.copy(
                            aviso = UiEvent.ShowSnackbar(player.message),
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    private fun deleteFavPlayer(credentialId: Int, playerId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            when (val result = deleteFavoritePlayerUseCase.invoke(credentialId, playerId)) {
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

}