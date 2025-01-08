package com.example.segundaapp_adriansaavedra.ui.pantallaMain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.example.segundaapp_adriansaavedra.R
import com.example.segundaapp_adriansaavedra.domain.modelo.Persona
import com.example.segundaapp_adriansaavedra.domain.usecases.personas.*
import com.example.segundaapp_adriansaavedra.utils.StringProvider


class MainViewModel(
    private val stringProvider: StringProvider,
    private val addPersonUseCase: AddPersonUseCase,
    private val getPersons: GetPersons,
    private val deletePersonUseCase: DeletePersonUseCase,
    private val updatePersonUseCase: UpdatePersonUseCase,
    private val getPersonId: GetPersonUseCase,
) : ViewModel() {
    private var indice = 0
    private val _uiState = MutableLiveData<MainState>()
    val uiState: LiveData<MainState> get() = _uiState

    init {
        _uiState.value = MainState(
            persona = getPersonId(0),
            aviso = null,
            anterior = false,
            indiceActual = 1,
            longitudLista = getPersons.invoke().size
        )
    }

    private fun updateIndiceLongitud(){
        _uiState.value = _uiState.value?.copy(
            indiceActual = indice + 1,
            longitudLista = getPersons.invoke().size
        )
    }

    fun addPersona(personaArg: Persona) {
        if (personaArg.nombre == "" || personaArg.nombre.isEmpty()) {
            _uiState.value =
                _uiState.value?.copy(aviso = stringProvider.getString(R.string.rellenanombre))
        } else {
            if (!addPersonUseCase(personaArg)) {
                _uiState.value =
                    _uiState.value?.copy(aviso = stringProvider.getString(R.string.erroragregar))
            } else {
                if (getPersons.invoke().size == 1) {
                    _uiState.value = MainState(
                        persona = _uiState.value.let { personaArg },
                        aviso = stringProvider.getString(R.string.personaagregada),
                        anterior = false,
                        siguiente = false,
                        updatedelete = true,
                    )
                } else {
                    _uiState.value = MainState(
                        persona = _uiState.value.let { personaArg },
                        aviso = stringProvider.getString(R.string.personaagregada),
                        anterior = true,
                        siguiente = false,
                        updatedelete = true,
                    )
                }
                indice = getPersons.invoke().size - 1
            }
        }
        updateIndiceLongitud()
    }

    fun deletePersona() {
        if (!deletePersonUseCase(indice)) {
            _uiState.value = MainState(
                aviso = stringProvider.getString(R.string.agregaprimero),
                anterior = false,
                siguiente = false,
                updatedelete = false,
            )
        } else {
            if (indice != 0) {
                indice--
            }
            _uiState.value = MainState(
                persona = getPersonId(indice),
                aviso = stringProvider.getString(R.string.personaeliminada),
            )
            if (indice == 0) {
                _uiState.value = _uiState.value?.copy(anterior = false)
            }
            if (indice == getPersons.invoke().size - 1) {
                _uiState.value = _uiState.value?.copy(siguiente = false)
            }
            if (getPersons.invoke().isEmpty()) {
                _uiState.value = MainState(
                    aviso = stringProvider.getString(R.string.agregaprimero),
                    anterior = false,
                    siguiente = false,
                    updatedelete = false,
                )
            }

        }
        updateIndiceLongitud()
    }

    fun updatePersona(newPersona: Persona) {
        if (newPersona.nombre == "" || newPersona.nombre.isEmpty()) {
            _uiState.value =
                _uiState.value?.copy(aviso = stringProvider.getString(R.string.rellenanombre))
        } else {
            updatePersonUseCase.invoke(_uiState.value!!.persona, newPersona)
            _uiState.value =
                _uiState.value?.copy(aviso = stringProvider.getString(R.string.personaactualizada))
            _uiState.value = _uiState.value?.copy(persona = newPersona)
        }
    }

    private fun getPersona(id: Int) {
        _uiState.value = MainState(
            persona = getPersonId(id),
            aviso = null,
        )
    }

    fun getSiguientePersona() {
        if (indice < getPersons.invoke().size - 1) {
            _uiState.value = _uiState.value?.copy(anterior = true)
            indice++
            getPersona(indice)
            if (indice == getPersons.invoke().size - 1) {
                _uiState.value = _uiState.value?.copy(siguiente = false)
            }
        }
        updateIndiceLongitud()
    }
    fun getAnteriorPersona (){
        if (indice > 0) {
            _uiState.value = _uiState.value?.copy(siguiente = true)
            indice--
            getPersona(indice)
            if (indice == 0) {
                _uiState.value = _uiState.value?.copy(anterior = false)
            }
        }
        updateIndiceLongitud()
    }
    fun avisoMostrado() {
        _uiState.value = _uiState.value?.copy(aviso = null)
    }


}

class MainViewModelFactory(
    private val stringProvider: StringProvider,
    private val addPersonUseCase: AddPersonUseCase,
    private val getPersons: GetPersons,
    private val deletePersonUseCase: DeletePersonUseCase,
    private val updatePersonUseCase: UpdatePersonUseCase,
    private val getPersonId: GetPersonUseCase

) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(
                stringProvider,
                addPersonUseCase,
                getPersons,
                deletePersonUseCase,
                updatePersonUseCase,
                getPersonId
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}