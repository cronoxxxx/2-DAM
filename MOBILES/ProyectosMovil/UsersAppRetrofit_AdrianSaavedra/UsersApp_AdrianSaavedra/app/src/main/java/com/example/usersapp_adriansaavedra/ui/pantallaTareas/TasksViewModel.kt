package com.example.usersapp_adriansaavedra.ui.pantallaTareas

import androidx.lifecycle.*
import com.example.usersapp_adriansaavedra.R
import com.example.usersapp_adriansaavedra.data.remote.NetworkResult
import com.example.usersapp_adriansaavedra.domain.usecases.tareas.GetTasksUseCase
import com.example.usersapp_adriansaavedra.ui.common.StringProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val getTasksUseCase: GetTasksUseCase,
    private val stringProvider: StringProvider,
) : ViewModel() {
    private val _uiState = MutableLiveData(TasksState())
    val uiState: LiveData<TasksState> = _uiState

    fun handleEvent(event: TasksEvent) {
        when (event) {
            is TasksEvent.GetTasks -> getTasks()
            is TasksEvent.AvisoVisto -> avisoMostrado()
        }
    }

    private fun getTasks() {
        viewModelScope.launch {
            when (val result = getTasksUseCase()) {
                is NetworkResult.Success -> {
                    _uiState.value = _uiState.value?.copy(
                        tasks = result.data
                    )
                }
                is NetworkResult.Error -> {
                    _uiState.value = _uiState.value?.copy(
                        aviso = stringProvider.getString(R.string.error_obteniendo_tareas)
                    )
                }
            }
        }
    }

    private fun avisoMostrado() {
        _uiState.value = _uiState.value?.copy(aviso = null)
    }
}