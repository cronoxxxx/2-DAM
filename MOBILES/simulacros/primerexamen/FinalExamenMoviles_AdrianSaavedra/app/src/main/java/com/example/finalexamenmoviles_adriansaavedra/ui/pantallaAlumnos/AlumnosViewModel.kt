package com.example.finalexamenmoviles_adriansaavedra.ui.pantallaAlumnos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalexamenmoviles_adriansaavedra.data.remote.NetworkResult
import com.example.finalexamenmoviles_adriansaavedra.domain.usecases.alumno.GetAlumnosUseCase
import com.example.finalexamenmoviles_adriansaavedra.ui.common.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlumnosViewModel @Inject constructor(private val getAlumnosUseCase: GetAlumnosUseCase) : ViewModel() {
    private val _uiState = MutableStateFlow(AlumnosState())
    val uiState = _uiState.asStateFlow()

    fun handleEvent(event: AlumnosEvent) {
        when(event) {
            is AlumnosEvent.AvisoVisto -> avisoVisto()
            is AlumnosEvent.GetAlumnos -> getAlumnos()

        }
    }

    private fun avisoVisto() {
        _uiState.update { it.copy(aviso = null) }
    }

    private fun getAlumnos() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            when (val result = getAlumnosUseCase.invoke()) {
                is NetworkResult.Success -> {
                    _uiState.update { it.copy(alumnos = result.data, isLoading = false) }
                }
                is NetworkResult.Error -> {
                    _uiState.update {
                        it.copy(
                            alumnos = emptyList(),
                            aviso = UiEvent.ShowSnackbar(result.message),
                            isLoading = false
                        )
                    }
                }
            }
        }
    }
}