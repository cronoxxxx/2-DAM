package com.example.finalexamenmoviles_adriansaavedra.ui.pantallaInformes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalexamenmoviles_adriansaavedra.data.remote.NetworkResult
import com.example.finalexamenmoviles_adriansaavedra.domain.usecases.informe.GetInformesUseCase
import com.example.finalexamenmoviles_adriansaavedra.domain.usecases.informe.InsertInitialInformesUseCase
import com.example.finalexamenmoviles_adriansaavedra.ui.common.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InformesViewModel @Inject constructor(
    private val getInformesUseCase: GetInformesUseCase,
    private val insertInitialInformesUseCase: InsertInitialInformesUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(InformesState())
    val uiState = _uiState.asStateFlow()
    
    fun handleEvent(event: InformesEvent) {
        when (event) {
            is InformesEvent.AvisoVisto -> avisoVisto()
            is InformesEvent.GetInformes -> getInformes()
            is InformesEvent.InformeSelected -> selectInforme(event.id)
            is InformesEvent.LoadInitialInformes -> loadInitialInformes()
        }
    }
    
    private fun avisoVisto() {
        _uiState.update { it.copy(aviso = null) }
    }
    
    private fun getInformes() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            when (val result = getInformesUseCase.invoke()) {
                is NetworkResult.Success -> {
                    _uiState.update { it.copy(informes = result.data, isLoading = false) }
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
    
    private fun selectInforme(id: Int) {
        _uiState.update {
            it.copy(
                selectedInformeId = id,
                aviso = UiEvent.Navigate
            )
        }
    }
    
    private fun loadInitialInformes() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            when (val result = insertInitialInformesUseCase.invoke()) {
                is NetworkResult.Success -> {
                    getInformes()
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

