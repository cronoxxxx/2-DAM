package com.example.usersapp_adriansaavedra.ui.pantallaTareas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.usersapp_adriansaavedra.R
import com.example.usersapp_adriansaavedra.data.remote.NetworkResult
import com.example.usersapp_adriansaavedra.domain.usecases.tareas.GetTasksUseCase
import com.example.usersapp_adriansaavedra.ui.common.StringProvider
import com.example.usersapp_adriansaavedra.ui.common.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val getTasksUseCase: GetTasksUseCase,
    private val stringProvider: StringProvider,
) : ViewModel() {
    private val _uiState = MutableStateFlow(TasksState())
    val uiState = _uiState.asStateFlow()

    fun handleEvent(event: TasksEvent) {
        when (event) {
            is TasksEvent.GetTasks -> getTasks(event.userId)
            is TasksEvent.AvisoVisto -> avisoMostrado()
        }
    }

    private fun getTasks(id:Int) {
        viewModelScope.launch {
            when (val result = getTasksUseCase(id)) {
                is NetworkResult.Success -> {
                    _uiState.update { currentState -> currentState.copy(tasks = result.data) }
                }

                is NetworkResult.Loading -> {
                    _uiState.update { currentState -> currentState.copy(isLoading = true) }
                }

                is NetworkResult.Error -> {
                    _uiState.update { currentState ->
                        currentState.copy(
                            aviso = UiEvent.ShowSnackbar(
                                stringProvider.getString(R.string.error_obteniendo_tareas)
                            )
                        )
                    }
                }
            }
        }
    }

    private fun avisoMostrado() {
        _uiState.update { currentState -> currentState.copy(aviso = null) }
    }
}