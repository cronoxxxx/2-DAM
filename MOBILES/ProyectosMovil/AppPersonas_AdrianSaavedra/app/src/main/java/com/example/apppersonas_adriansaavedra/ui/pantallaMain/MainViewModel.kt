package com.example.apppersonas_adriansaavedra.ui.pantallaMain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apppersonas_adriansaavedra.R
import com.example.apppersonas_adriansaavedra.domain.modelo.Persona
import com.example.apppersonas_adriansaavedra.domain.usecases.personas.*
import com.example.apppersonas_adriansaavedra.ui.Constantes
import com.example.apppersonas_adriansaavedra.ui.common.StringProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getPersonsUseCase: GetPersonsUseCase,
    private val deletePersonUseCase: DeletePersonUseCase,
    private val stringProvider: StringProvider
) : ViewModel() {
    private val _uiState = MutableLiveData<MainState>()
    val uiState: LiveData<MainState> get() = _uiState

    private var selectedPersonas = mutableListOf<Persona>()

    init {
        _uiState.value = MainState()
        getPersonas()
    }

    fun handleEvent(event: MainEvent) {
        when (event) {
            is MainEvent.GetPersonas -> getPersonas()
            is MainEvent.AvisoVisto -> avisoMostrado()
            is MainEvent.DeletePersona -> deletePersona(event.persona)
            is MainEvent.SeleccionaPersona -> seleccionaPersona(event.persona)
            is MainEvent.DeletePersonasSeleccionadas -> deletePersonasSeleccionadas()
            is MainEvent.StartSelectMode -> startSelectMode()
            is MainEvent.ResetSelectMode -> resetSelectMode()
            is MainEvent.RemovePersonaFromSelection -> removePersonaFromSelection(event.persona)
            is MainEvent.AvisoSelected -> showSelectedMessage()
        }
    }

    private fun startSelectMode() {
        Timber.d(Constantes.EMPEZANDO_SELECT_MODE)
        _uiState.value = _uiState.value?.copy(selectMode = true)
    }

    private fun getPersonas() {
        _uiState.value = _uiState.value?.copy(personas = getPersonsUseCase())
    }

    private fun deletePersona(persona: Persona) {
        Timber.d(Constantes.ELIMINANDO_PERSONA)
        viewModelScope.launch {
            if (deletePersonUseCase.invoke(persona)) {
                _uiState.value = _uiState.value?.copy(
                    aviso = stringProvider.getString(R.string.personaeliminada)
                )
                _uiState.value = _uiState.value?.copy(
                    personas = _uiState.value?.personas?.filter { it.id != persona.id }
                        ?: emptyList()
                )
            }
        }
    }

    private fun seleccionaPersona(persona: Persona) {
        val currentState = _uiState.value ?: MainState()
        val currentSelected = currentState.personasSelected.toMutableList()
        if (currentSelected.contains(persona)) {
            currentSelected.remove(persona)
        } else {
            currentSelected.add(persona)
        }
        _uiState.value = currentState.copy(personasSelected = currentSelected)
        Timber.d(Constantes.PERSONA_SELECCIONADA)
    }

    private fun deletePersonasSeleccionadas() {
        Timber.d(Constantes.ELIMINANDO_PERSONAS_SELECCIONADAS)
        viewModelScope.launch {
            val personasToDelete = _uiState.value?.personasSelected ?: emptyList()
            for (persona in personasToDelete) {
                deletePersonUseCase.invoke(persona)
            }
            val updatedList = _uiState.value?.personas?.filter { it !in personasToDelete }
                ?: emptyList()
            _uiState.value = _uiState.value?.copy(
                personas = updatedList,
                personasSelected = emptyList(),
                selectMode = false,
                aviso = stringProvider.getString(R.string.personas_eliminadas) + "${personasToDelete.size}"
            )
        }
    }

    private fun removePersonaFromSelection(persona: Persona) {
        val currentState = _uiState.value ?: MainState()
        val updatedSelected = currentState.personasSelected.toMutableList()
        updatedSelected.remove(persona)
        _uiState.value = currentState.copy(personasSelected = updatedSelected)
    }

    private fun resetSelectMode() {
        Timber.d(Constantes.RESETEANDO_SELECT_MODE)
        selectedPersonas.clear()
        _uiState.value = _uiState.value?.copy(selectMode = false, personasSelected = emptyList())
    }

    private fun avisoMostrado() {
        _uiState.value = _uiState.value?.copy(aviso = null)
    }

    private fun showSelectedMessage() {
        if (_uiState.value?.selectMode == true) {
            val message = stringProvider.getString(R.string.desactivar_select_mode)
            _uiState.value = _uiState.value?.copy(aviso = message)
        }
    }
}

