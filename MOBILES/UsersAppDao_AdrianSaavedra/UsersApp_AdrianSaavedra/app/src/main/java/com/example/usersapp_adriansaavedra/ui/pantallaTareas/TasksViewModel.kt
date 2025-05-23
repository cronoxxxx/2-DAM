package com.example.usersapp_adriansaavedra.ui.pantallaTareas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.usersapp_adriansaavedra.data.remote.NetworkResult
import com.example.usersapp_adriansaavedra.domain.usecases.tareas.GetTasksUseCase
import com.example.usersapp_adriansaavedra.ui.common.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val getTasksUseCase: GetTasksUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(TasksState())
    val uiState = _uiState.asStateFlow()

    fun handleEvent(event: TasksEvent) {
        when (event) {
            is TasksEvent.GetTasks -> getTasks(event.userId)
            is TasksEvent.AvisoVisto -> avisoMostrado()
        }
    }

    private fun getTasks(id: Int) {
        viewModelScope.launch {
            val result = getTasksUseCase(id)
            result.collect { obtained ->
                when (obtained) {
                    is NetworkResult.Success -> {
                        _uiState.update { currentState ->
                            currentState.copy(
                                tasks = obtained.data,
                                isLoading = false
                            )
                        }
                    }

                    is NetworkResult.Loading -> {
                        _uiState.update { currentState -> currentState.copy(isLoading = true) }
                    }

                    is NetworkResult.Error -> {
                        _uiState.update { currentState ->
                            currentState.copy(
                                aviso = UiEvent.ShowSnackbar(
                                    obtained.message
                                ), isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }


    private fun avisoMostrado() {
        _uiState.update { currentState -> currentState.copy(aviso = null) }
    }
}