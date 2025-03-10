package com.example.recuperacion_adriansaavedra.ui.pSegundaLista

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalexamenmoviles_adriansaavedra.ui.common.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SegundaListaViewModel @Inject constructor() : ViewModel() {
    // Aquí puedes inyectar los casos de uso necesarios
    // private val getSegundaListaUseCase: GetSegundaListaUseCase,
    
    private val _uiState = MutableStateFlow(SegundaListaState())
    val uiState = _uiState.asStateFlow()

    fun handleEvent(event: SegundaListaEvent) {
        when (event) {
            is SegundaListaEvent.AvisoVisto -> avisoVisto()
            is SegundaListaEvent.CargarDatos -> cargarDatos()
            is SegundaListaEvent.ItemSeleccionado -> seleccionarItem(event.item)
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
            val datosEjemplo = listOf("Elemento A", "Elemento B", "Elemento C")
            
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