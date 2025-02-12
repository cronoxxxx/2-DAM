package com.example.usersapp_adriansaavedra.ui.pantallaAgregar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.usersapp_adriansaavedra.R
import com.example.usersapp_adriansaavedra.data.remote.NetworkResult
import com.example.usersapp_adriansaavedra.domain.modelo.User
import com.example.usersapp_adriansaavedra.domain.usecases.usuarios.AddUserUseCase
import com.example.usersapp_adriansaavedra.ui.common.StringProvider
import com.example.usersapp_adriansaavedra.ui.common.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddUsuarioViewModel @Inject constructor(
    private val stringProvider: StringProvider,
    private val addUserUseCase: AddUserUseCase
) : ViewModel() {
    private val _uiState = MutableLiveData(AddUsuarioState())
    val uiState: LiveData<AddUsuarioState> = _uiState

    fun handleEvent(event: AddUsuarioEvent) {
        when (event) {
            is AddUsuarioEvent.AddUser -> addUser(event.user)
            AddUsuarioEvent.AvisoVisto -> avisoMostrado()
        }
    }

    private fun addUser(user: User) {
        if (user.name.isEmpty() || user.email.isEmpty() || user.username.isEmpty()) {
            _uiState.value = _uiState.value?.copy(
                aviso = UiEvent.ShowSnackbar(stringProvider.getString(R.string.campos_vacios))
            )
        } else {
            viewModelScope.launch {
                when (addUserUseCase(user)) {
                    is NetworkResult.Success -> {
                        _uiState.value = _uiState.value?.copy(
                            aviso = UiEvent.ShowSnackbar(stringProvider.getString(R.string.usuario_agregado_exitosamente))
                        )
                        _uiState.value = _uiState.value?.copy(aviso = UiEvent.PopBackStack)
                    }
                    is NetworkResult.Error -> {
                        _uiState.value = _uiState.value?.copy(
                            aviso = UiEvent.ShowSnackbar(stringProvider.getString(R.string.error_agregando_usuario)),
                        )
                    }
                }
            }
        }
    }

    private fun avisoMostrado() {
        _uiState.value = _uiState.value?.copy(aviso = null)
    }
}