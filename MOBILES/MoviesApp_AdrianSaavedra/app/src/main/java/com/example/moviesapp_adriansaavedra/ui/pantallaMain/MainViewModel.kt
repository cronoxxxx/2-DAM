package com.example.moviesapp_adriansaavedra.ui.pantallaMain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp_adriansaavedra.R
import com.example.moviesapp_adriansaavedra.data.remote.NetworkResult
import com.example.moviesapp_adriansaavedra.domain.usecases.peliculas.GetMoviesUseCase
import com.example.moviesapp_adriansaavedra.ui.Constantes
import com.example.moviesapp_adriansaavedra.ui.common.StringProvider
import com.example.moviesapp_adriansaavedra.ui.common.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val stringProvider: StringProvider
) : ViewModel() {

    private val _uiState = MutableLiveData<MainState>()
    val uiState: LiveData<MainState> get() = _uiState
    init {
        _uiState.value = MainState()
        getMovies()
    }



    fun handleEvent(event: MainEvent) {
        when (event) {
            is MainEvent.GetMovies -> getMovies()
            is MainEvent.AvisoVisto -> avisoMostrado()

        }

}

    private fun avisoMostrado() {
        _uiState.value = _uiState.value?.copy(aviso = null)
    }

    private fun getMovies() {
        Timber.d(Constantes.CARGANDO_INFORMACION_PELICULAS)
        viewModelScope.launch {
            when (val result = getMoviesUseCase()) {
                is NetworkResult.Success -> {
                    Timber.d(Constantes.PELICULAS_OBTENIDAS_EXITOSAMENTE, result.data?.size)
                    _uiState.value = _uiState.value?.copy(
                        movies = result.data ?: emptyList(),
                    )
                }
                is NetworkResult.Error -> {
                    Timber.i(Constantes.ERROR_OBTENIENDO_PELICULAS + result.message)
                    _uiState.value = _uiState.value?.copy(
                        aviso = UiEvent.ShowSnackbar(stringProvider.getString(R.string.error_obteniendo_peliculas)),
                    )
                }
            }
        }
    }

}
