package com.example.hospitalapp_adriansaavedra.ui.pantallaMedRecord

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hospitalapp_adriansaavedra.data.remote.NetworkResult
import com.example.hospitalapp_adriansaavedra.domain.usecases.records.GetPatientRecordUseCase
import com.example.hospitalapp_adriansaavedra.ui.common.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MedRecordDetailViewModel @Inject constructor(
    private val getPatientRecordUseCase: GetPatientRecordUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(MedRecordDetailState())
    val uiState = _uiState.asStateFlow()

    fun handleEvent(event: MedRecordDetailEvent) {
        when (event) {
            is MedRecordDetailEvent.GetPatientRecord -> getPatientRecord(event.patientId, event.recordId)
        }
    }

    private fun getPatientRecord(patientId: Int, recordId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            getPatientRecordUseCase(patientId, recordId).collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        _uiState.update {
                            it.copy(
                                medRecord = result.data,
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