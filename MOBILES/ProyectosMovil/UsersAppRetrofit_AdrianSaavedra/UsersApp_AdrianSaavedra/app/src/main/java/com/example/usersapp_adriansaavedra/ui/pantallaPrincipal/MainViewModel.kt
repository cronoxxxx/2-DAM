package com.example.usersapp_adriansaavedra.ui.pantallaPrincipal

import androidx.lifecycle.*
import com.example.usersapp_adriansaavedra.R
import com.example.usersapp_adriansaavedra.data.remote.NetworkResult
import com.example.usersapp_adriansaavedra.domain.usecases.usuarios.GetUsersUseCase
import com.example.usersapp_adriansaavedra.ui.common.StringProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase,
    private val stringProvider: StringProvider
) : ViewModel() {

    private val _uiState = MutableLiveData<MainState>()
    val uiState: LiveData<MainState> get() = _uiState

    init {
        _uiState.value = MainState()
        getUsers()
    }

    fun handleEvent(event: MainEvent) {
        when (event) {
            is MainEvent.GetUsers -> getUsers()
            is MainEvent.AvisoVisto -> avisoVisto()
        }
    }

    private fun avisoVisto() {
        _uiState.value = _uiState.value?.copy(aviso = null)
    }

    private fun getUsers() {
        viewModelScope.launch {
            when (val result = getUsersUseCase()) {
                is NetworkResult.Success -> {
                    _uiState.value = _uiState.value?.copy(
                        users = result.data,
                    )
                }
                is NetworkResult.Error -> {
                    _uiState.value = _uiState.value?.copy(
                        aviso = stringProvider.getString(R.string.error_obteniendo_usuarios)
                    )
                }
            }
        }
    }
}