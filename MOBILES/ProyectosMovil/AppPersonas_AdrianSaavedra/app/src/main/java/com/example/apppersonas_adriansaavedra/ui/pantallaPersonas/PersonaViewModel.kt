package com.example.apppersonas_adriansaavedra.ui.pantallaPersonas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apppersonas_adriansaavedra.R
import com.example.apppersonas_adriansaavedra.domain.modelo.Persona
import com.example.apppersonas_adriansaavedra.domain.usecases.personas.*
import com.example.apppersonas_adriansaavedra.ui.Constantes
import com.example.apppersonas_adriansaavedra.ui.common.StringProvider
import com.example.apppersonas_adriansaavedra.ui.common.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PersonaViewModel @Inject constructor(
    private val stringProvider: StringProvider,
    private val getPersonUseCase: GetPersonUseCase,
    private val deletePersonUseCase: DeletePersonUseCase,
    private val updatePersonUseCase: UpdatePersonUseCase
) : ViewModel() {
    private val _uiState = MutableLiveData(PersonaState())
    val uiState: LiveData<PersonaState> = _uiState

    fun handleEvent(event: PersonaEvent) {
        when (event) {
            is PersonaEvent.GetPersona -> getPersona(event.id)
            is PersonaEvent.UpdatePersona -> updatePersona(event.persona)
            is PersonaEvent.DeletePersona -> deletePersona()
            is PersonaEvent.AvisoVisto -> avisoMostrado()
        }
    }

    private fun getPersona(id: Int) {
        Timber.d(Constantes.OBTENIENDO_PERSONA_CON_ID + id)
        val persona = getPersonUseCase(id)
        _uiState.value = _uiState.value?.copy(persona = persona)
    }

    private fun updatePersona(newPersona: Persona) {
        if (newPersona.nombre.isEmpty()) {
            _uiState.value = _uiState.value?.copy(aviso = UiEvent.ShowSnackbar(stringProvider.getString(R.string.rellenanombre)))
        } else {
            Timber.d(Constantes.PERSONA_ACTUALIZANDO)
            val currentPersona = _uiState.value?.persona
            if (currentPersona != null) {
                Timber.d(Constantes.PERSONA_ACTUALIZADA)
                updatePersonUseCase(currentPersona, newPersona)
                _uiState.value = _uiState.value?.copy(
                    persona = newPersona,
                    aviso = UiEvent.ShowSnackbar(stringProvider.getString(R.string.personaactualizada))
                )
                _uiState.value = _uiState.value?.copy(aviso = UiEvent.PopBackStack)
            }
        }
    }

    private fun deletePersona() {
        Timber.d(Constantes.ELIMINANDO_PERSONA)
        val currentPersona = _uiState.value?.persona
        if (currentPersona != null && deletePersonUseCase(currentPersona)) {
            Timber.d(Constantes.PERSONA_ELIMINADA)
            _uiState.value = _uiState.value?.copy(
                aviso = UiEvent.ShowSnackbar(stringProvider.getString(R.string.personaeliminada))
            )
            _uiState.value = _uiState.value?.copy(aviso = UiEvent.PopBackStack)
        } else {
            _uiState.value = _uiState.value?.copy(
                aviso = UiEvent.ShowSnackbar(stringProvider.getString(R.string.agregaprimero))
            )
        }
    }

    private fun avisoMostrado() {
        _uiState.value = _uiState.value?.copy(aviso = null)
    }
}

