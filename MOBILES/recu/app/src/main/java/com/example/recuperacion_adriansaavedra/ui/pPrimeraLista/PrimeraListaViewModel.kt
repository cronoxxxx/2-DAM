package com.example.recuperacion_adriansaavedra.ui.pPrimeraLista

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalexamenmoviles_adriansaavedra.ui.common.UiEvent
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PrimeraListaViewModel @Inject constructor() : ViewModel() {
    // Aquí puedes inyectar los casos de uso necesarios
    // private val getPrimeraListaUseCase: GetPrimeraListaUseCase,
    
    private val _uiState = MutableStateFlow(PrimeraListaState())
    val uiState = _uiState.asStateFlow()

    fun handleEvent(event: PrimeraListaEvent) {
        when (event) {
            is PrimeraListaEvent.AvisoVisto -> avisoVisto()
            is PrimeraListaEvent.CargarDatos -> cargarDatos()
            is PrimeraListaEvent.ItemSeleccionado -> seleccionarItem(event.item)
        }
    }

    private fun avisoVisto() {
        _uiState.update { it.copy(aviso = null) }
    }

    private fun cargarDatos() {
        // DURANTE EL EXAMEN: Implementar la carga real de datos
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            
            // Simular carga de datos
            delay(500)
            
            // Datos de ejemplo
            val datosEjemplo = listOf("Item 1", "Item 2", "Item 3")
            
            _uiState.update { 
                it.copy(
                    items = datosEjemplo,
                    isLoading = false
                ) 
            }
        }
    }

    private fun seleccionarItem(item: String) {
        // DURANTE EL EXAMEN: Implementar la lógica de selección
        _uiState.update { 
            it.copy(
                aviso = UiEvent.ShowSnackbar("Seleccionado: $item")
            ) 
        }
    }
}