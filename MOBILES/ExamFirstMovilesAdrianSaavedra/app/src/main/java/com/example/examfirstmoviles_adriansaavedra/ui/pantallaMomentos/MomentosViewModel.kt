package com.example.examfirstmoviles_adriansaavedra.ui.pantallaMomentos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examfirstmoviles_adriansaavedra.R
import com.example.examfirstmoviles_adriansaavedra.data.remote.NetworkResult
import com.example.examfirstmoviles_adriansaavedra.domain.usecases.equipo.GetEquiposUseCase
import com.example.examfirstmoviles_adriansaavedra.ui.common.StringProvider
import com.example.examfirstmoviles_adriansaavedra.ui.pantallaPrincipal.MomentosEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MomentosViewModel @Inject constructor (
    private val getEquiposUseCase: GetEquiposUseCase,
    private val stringProvider: StringProvider
) : ViewModel() {
    private val _uiState = MutableLiveData<MomentosState>()
    val uiState: LiveData<MomentosState> get() = _uiState

    init{
        _uiState.value = MomentosState()
        getUsers()
    }

    fun handleEvent (event : MomentosEvent){
        when(event){
            is MomentosEvent.GetEquipos -> getUsers()
            is MomentosEvent.AvisoVisto -> avisoMostrado()
        }
    }


    private fun getUsers() {
        viewModelScope.launch {
            when (val result = getEquiposUseCase()) {
                is NetworkResult.Success -> {
                    _uiState.value = _uiState.value?.copy(
                        equipos = result.data,
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

    private fun avisoMostrado() {
        _uiState.value = _uiState.value?.copy(aviso = null)
    }
}