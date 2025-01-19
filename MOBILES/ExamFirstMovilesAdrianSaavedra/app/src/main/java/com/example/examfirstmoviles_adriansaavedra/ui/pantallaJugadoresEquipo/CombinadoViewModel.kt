package com.example.examfirstmoviles_adriansaavedra.ui.pantallaJugadoresEquipo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examfirstmoviles_adriansaavedra.R
import com.example.examfirstmoviles_adriansaavedra.data.remote.NetworkResult
import com.example.examfirstmoviles_adriansaavedra.domain.usecases.equipo.GetEquiposUseCase
import com.example.examfirstmoviles_adriansaavedra.domain.usecases.equipo.GetPlayersUseCase
import com.example.examfirstmoviles_adriansaavedra.ui.common.StringProvider
import com.example.examfirstmoviles_adriansaavedra.ui.pantallaMomentos.MomentosState
import com.example.examfirstmoviles_adriansaavedra.ui.pantallaPrincipal.MomentosEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CombinadoViewModel @Inject constructor (
    private val getPlayersUseCase: GetPlayersUseCase,
    private val stringProvider: StringProvider
) : ViewModel() {
    private val _uiState = MutableLiveData<CombinadoState>()
    val uiState: LiveData<CombinadoState> get() = _uiState

    init {
        _uiState.value = CombinadoState()
    }

    fun handleEvent(event: CombinadoEvent) {
        when (event) {
            is CombinadoEvent.GetPlayers -> getUsers(event.id)
            is CombinadoEvent.AvisoVisto -> avisoMostrado()
        }
    }


    private fun getUsers(id: Int) {
        viewModelScope.launch {
            when (val result = getPlayersUseCase.invoke(id)) {
                is NetworkResult.Success -> {
                    _uiState.value = _uiState.value?.copy(
                        players = result.data,
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