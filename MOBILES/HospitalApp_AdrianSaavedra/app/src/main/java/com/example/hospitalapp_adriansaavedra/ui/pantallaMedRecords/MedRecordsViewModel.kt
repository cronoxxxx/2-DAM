package com.example.hospitalapp_adriansaavedra.ui.pantallaMedRecords

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hospitalapp_adriansaavedra.data.remote.NetworkResult
import com.example.hospitalapp_adriansaavedra.domain.usecases.records.GetPatientRecordsUseCase
import com.example.hospitalapp_adriansaavedra.ui.common.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MedRecordsViewModel @Inject constructor(private val getPatientRecordsUseCase: GetPatientRecordsUseCase) :
    ViewModel() {
    private val _uiState = MutableStateFlow(MedRecordsState())
    val uiState = _uiState.asStateFlow()


    fun handleEvent(event: MedRecordsEvent) {
        when (event) {
            is MedRecordsEvent.GetPatientRecords -> getPatientRecords(event.id)
        }
    }

    private fun getPatientRecords(id: Int) {
        viewModelScope.launch {
            getPatientRecordsUseCase(id).collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        _uiState.update {
                            it.copy(
                                patientsRecords = result.data,
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

                    is NetworkResult.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }
                }
            }
        }
    }
}