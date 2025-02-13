package com.example.hospitalapp_adriansaavedra.ui.pantallaPaciente

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hospitalapp_adriansaavedra.data.remote.NetworkResult
import com.example.hospitalapp_adriansaavedra.domain.usecases.patients.GetPatientUseCase
import com.example.hospitalapp_adriansaavedra.ui.common.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PatientDetailViewModel @Inject constructor(
    private val getPatientUseCase: GetPatientUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(PatientDetailState())
    val uiState = _uiState.asStateFlow()

    fun handleEvent(event: PatientDetailEvent) {
        when (event) {
            is PatientDetailEvent.GetPatient -> getPatient(event.id)
            is PatientDetailEvent.AvisoVisto -> avisoVisto()
        }
    }

    private fun avisoVisto() {
        _uiState.update { it.copy(aviso = null) }
    }

    private fun getPatient(id: Int) {
        viewModelScope.launch {
            getPatientUseCase(id).collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        _uiState.update {
                            it.copy(patient = result.data, isLoading = false)
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

                    is NetworkResult.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }
                }
            }
        }
    }
}