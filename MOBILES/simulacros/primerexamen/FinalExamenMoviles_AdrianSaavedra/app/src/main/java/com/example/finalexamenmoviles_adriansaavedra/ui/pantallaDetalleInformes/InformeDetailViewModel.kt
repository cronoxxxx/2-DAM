package com.example.finalexamenmoviles_adriansaavedra.ui.pantallaDetalleInformes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalexamenmoviles_adriansaavedra.data.remote.NetworkResult
import com.example.finalexamenmoviles_adriansaavedra.domain.usecases.informe.GetInformeUseCase
import com.example.finalexamenmoviles_adriansaavedra.domain.usecases.informe.UpdateInformeUseCase
import com.example.finalexamenmoviles_adriansaavedra.ui.common.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InformeDetailViewModel @Inject constructor(
    private val getInformeUseCase: GetInformeUseCase,
    private val updateInformeUseCase: UpdateInformeUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(InformeDetailState())
    val uiState = _uiState.asStateFlow()
    
    fun handleEvent(event: InformeDetailEvent) {
        when (event) {
            is InformeDetailEvent.AvisoVisto -> avisoVisto()
            is InformeDetailEvent.GetInforme -> getInforme(event.id)
            is InformeDetailEvent.UpdateNivel -> updateNivel(event.nivel)
        }
    }
    
    private fun avisoVisto() {
        _uiState.update { it.copy(aviso = null) }
    }
    
    private fun getInforme(id: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            when (val result = getInformeUseCase.invoke(id)) {
                is NetworkResult.Success -> {
                    _uiState.update { it.copy(informe = result.data, isLoading = false) }
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
    
    private fun updateNivel(nivel: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val updatedInforme = _uiState.value.informe.copy(nivel = nivel)
            when (val result = updateInformeUseCase.invoke(updatedInforme)) {
                is NetworkResult.Success -> {
                    _uiState.update { 
                        it.copy(
                            informe = updatedInforme, 
                            isLoading = false,
                            aviso = UiEvent.ShowSnackbar("Nivel actualizado correctamente")
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
}

