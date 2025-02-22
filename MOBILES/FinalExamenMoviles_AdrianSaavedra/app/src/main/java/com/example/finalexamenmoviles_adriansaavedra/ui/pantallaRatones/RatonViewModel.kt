package com.example.finalexamenmoviles_adriansaavedra.ui.pantallaRatones

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalexamenmoviles_adriansaavedra.data.remote.NetworkResult
import com.example.finalexamenmoviles_adriansaavedra.data.remote.model.Raton
import com.example.finalexamenmoviles_adriansaavedra.domain.usecases.AddRaton
import com.example.finalexamenmoviles_adriansaavedra.domain.usecases.GetRatones
import com.example.finalexamenmoviles_adriansaavedra.ui.common.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RatonViewModel @Inject constructor(private val getRatones: GetRatones,
    private val addRaton :AddRaton) : ViewModel() {
    private val _uiState = MutableStateFlow(RatonState())
    val uiState = _uiState.asStateFlow()

    fun handleEvent(ratonEvent: RatonEvent) {
        when (ratonEvent) {
            is RatonEvent.AvisoVisto -> avisoVisto()
            is RatonEvent.GetRats -> getRatones()
            is RatonEvent.AddRat -> addRaton(ratonEvent.name)
        }
    }

    private fun avisoVisto() {
        _uiState.update { it.copy(aviso = null) }
    }

    private fun getRatones() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            when (val result = getRatones.invoke()) {
                is NetworkResult.Success -> {
                    _uiState.update { it.copy(rat = result.data, isLoading = false) }
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

    private fun addRaton(name: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            when (val result = addRaton.invoke(Raton(name))) {
                is NetworkResult.Success -> {
                    _uiState.update { it.copy(aviso=UiEvent.ShowSnackbar("aceptado"), isLoading = false) }
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