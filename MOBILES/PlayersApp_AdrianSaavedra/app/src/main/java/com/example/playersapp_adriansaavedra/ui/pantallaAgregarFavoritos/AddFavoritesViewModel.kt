package com.example.playersapp_adriansaavedra.ui.pantallaAgregarFavoritos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playersapp_adriansaavedra.data.PreferencesRepository
import com.example.playersapp_adriansaavedra.data.remote.NetworkResult
import com.example.playersapp_adriansaavedra.data.remote.utils.PlayerNameRequest
import com.example.playersapp_adriansaavedra.domain.usecases.favorites.AddFavoritePlayerUseCase
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
class AddFavoritesViewModel @Inject constructor(
    private val addFavoritePlayerUseCase: AddFavoritePlayerUseCase,
    val preferencesRepository: PreferencesRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(AddFavoritesState())
    val uiState = _uiState.asStateFlow()
    val userId = preferencesRepository.userId
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )

    fun handleEvent(event: AddFavoritesEvent) {
        when (event) {
            is AddFavoritesEvent.AvisoVisto -> avisoMostrado()
            is AddFavoritesEvent.AddFavoritePlayer -> addFavoritePlayer(
                event.userId,
                event.playerName
            )

            is AddFavoritesEvent.OnNameChange -> updateName(event.value)
            is AddFavoritesEvent.UpdateUserId -> updateUserId(event.userId)
        }
    }

    private fun updateUserId(userId: Int) {
        _uiState.update { it.copy(userId = userId) }
    }

    private fun avisoMostrado() {
        _uiState.value = _uiState.value.copy(aviso = null)
    }

    private fun addFavoritePlayer(userId: Int, playerName: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            when (val result =
                addFavoritePlayerUseCase.invoke(userId, PlayerNameRequest(playerName))) {
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

    private fun updateName(value: String) {
        _uiState.update { it.copy(playerName = value) }
    }
}