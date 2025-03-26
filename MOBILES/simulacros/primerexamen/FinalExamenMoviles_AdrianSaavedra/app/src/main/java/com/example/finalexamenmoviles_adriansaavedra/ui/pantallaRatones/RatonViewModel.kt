package com.example.finalexamenmoviles_adriansaavedra.ui.pantallaRatones

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalexamenmoviles_adriansaavedra.data.remote.NetworkResult
import com.example.finalexamenmoviles_adriansaavedra.domain.model.Raton
import com.example.finalexamenmoviles_adriansaavedra.domain.usecases.raton.AddRatonUseCase
import com.example.finalexamenmoviles_adriansaavedra.domain.usecases.raton.GetRatonesUseCase
import com.example.finalexamenmoviles_adriansaavedra.ui.common.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RatonViewModel @Inject constructor(
    private val getRatonesUseCase: GetRatonesUseCase,
    private val addRatonUseCase: AddRatonUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(RatonState())
    val uiState = _uiState.asStateFlow()

    fun handleEvent(ratonEvent: RatonEvent) {
        when (ratonEvent) {
            is RatonEvent.AvisoVisto -> avisoVisto()
            is RatonEvent.GetRats -> getRatones()
            is RatonEvent.AddRat -> addRaton(ratonEvent.name)
            is RatonEvent.OnNameChange -> updateName(ratonEvent.name)
        }
    }

    private fun avisoVisto() {
        _uiState.update { it.copy(aviso = null) }
    }

    private fun getRatones() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            when (val result = getRatonesUseCase.invoke()) {
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
            when (val result = addRatonUseCase.invoke(Raton(name))) {
                is NetworkResult.Success -> {
                    _uiState.update {
                        it.copy(
                            aviso = UiEvent.ShowSnackbar("Raton agregado"),
                            isLoading = false,
                            rat = it.rat + result.data
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
        _uiState.update { it.copy(name = value) }
    }
}