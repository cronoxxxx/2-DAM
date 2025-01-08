package com.example.apppersonas_adriansaavedra.ui.pantallaAgregar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apppersonas_adriansaavedra.R
import com.example.apppersonas_adriansaavedra.domain.modelo.Persona
import com.example.apppersonas_adriansaavedra.domain.usecases.personas.AddPersonUseCase
import com.example.apppersonas_adriansaavedra.ui.Constantes
import com.example.apppersonas_adriansaavedra.ui.common.StringProvider
import com.example.apppersonas_adriansaavedra.ui.common.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AddPersonaViewModel @Inject constructor(
    private val stringProvider: StringProvider,
    private val addPersonUseCase: AddPersonUseCase
) : ViewModel() {
    private val _uiState = MutableLiveData(AddPersonaState())
    val uiState: LiveData<AddPersonaState> = _uiState

    fun handleEvent(event: AddPersonaEvent) {
        when (event) {
            is AddPersonaEvent.AddPersona -> addPersona(event.persona)
            AddPersonaEvent.ErrorVisto -> avisoMostrado()
        }
    }

    private fun addPersona(personaArg: Persona) {
        if (personaArg.nombre.isEmpty()) {
            _uiState.value = _uiState.value?.copy(
                aviso = UiEvent.ShowSnackbar(stringProvider.getString(R.string.rellenanombre))
            )
        } else {
            if (addPersonUseCase(personaArg)) {
                Timber.d(Constantes.PERSONA_AGREGADA)
                _uiState.value = _uiState.value?.copy(
                    aviso = UiEvent.ShowSnackbar(stringProvider.getString(R.string.personaagregada))
                )
                _uiState.value = _uiState.value?.copy(aviso = UiEvent.PopBackStack)
            } else {
                _uiState.value = _uiState.value?.copy(
                    aviso = UiEvent.ShowSnackbar(stringProvider.getString(R.string.erroragregar))
                )
            }
        }
    }

    private fun avisoMostrado() {
        _uiState.value = _uiState.value?.copy(aviso = null)
    }
}
