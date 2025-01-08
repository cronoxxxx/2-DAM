package com.example.apppersonas_adriansaavedra.ui.pantallaMain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apppersonas_adriansaavedra.domain.usecases.personas.GetPersonsUseCase
import com.example.apppersonas_adriansaavedra.ui.Constantes
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val getPersonsUseCase: GetPersonsUseCase) : ViewModel() {
    private val _uiState = MutableLiveData<MainState>()
    val uiState: LiveData<MainState> get() = _uiState

    init {
        _uiState.value = MainState(
            aviso = null
        )
    }

    fun handleEvent(event: MainEvent) {
        when (event) {
            is MainEvent.GetPersonas -> getPersonas()
            is MainEvent.ErrorVisto -> avisoMostrado()
        }
    }

    private fun getPersonas() {
        _uiState.value = _uiState.value?.copy(personas = getPersonsUseCase())
        Timber.d(Constantes.PERSONAS_OBTENIDAS + getPersonsUseCase().size)
    }

    private fun avisoMostrado() {
        _uiState.value = _uiState.value?.copy(aviso = null)
    }
}

