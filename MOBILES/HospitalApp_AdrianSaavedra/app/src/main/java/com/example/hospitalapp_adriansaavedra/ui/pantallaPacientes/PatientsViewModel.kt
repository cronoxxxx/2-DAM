package com.example.hospitalapp_adriansaavedra.ui.pantallaPacientes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hospitalapp_adriansaavedra.data.remote.NetworkResult
import com.example.hospitalapp_adriansaavedra.domain.usecases.patients.GetPatientsUseCase
import com.example.hospitalapp_adriansaavedra.ui.common.UiEvent
import com.example.hospitalapp_adriansaavedra.ui.pantallaMedRecords.MedRecordsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PatientsViewModel @Inject constructor(private val getPatientsUseCase: GetPatientsUseCase): ViewModel() {
    private val _uiState = MutableStateFlow(PatientsState())
    val uiState = _uiState.asStateFlow()

    fun handleEvent(event: PatientsEvent) {
        when (event) {
            is PatientsEvent.GetPatients -> getPatients()
        }
    }

    private fun getPatients() {
        viewModelScope.launch {
            getPatientsUseCase().collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        _uiState.update { currentState -> currentState.copy(patients = result.data, isLoading = false) }
                    }
                    is NetworkResult.Error -> {
                        _uiState.update { currentState -> currentState.copy(aviso = UiEvent.ShowSnackbar(result.message), isLoading = false) }
                    }
                    is NetworkResult.Loading -> {
                        _uiState.update { currentState -> currentState.copy(isLoading = true) }
                    }
                }
            }
        }
    }
}