package com.example.moviesapp_adriansaavedra.ui.pantallaPeliculas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp_adriansaavedra.R
import com.example.moviesapp_adriansaavedra.data.remote.NetworkResult
import com.example.moviesapp_adriansaavedra.domain.modelo.Movie
import com.example.moviesapp_adriansaavedra.domain.usecases.peliculas.GetMovieUseCase
import com.example.moviesapp_adriansaavedra.ui.Constantes
import com.example.moviesapp_adriansaavedra.ui.common.StringProvider
import com.example.moviesapp_adriansaavedra.ui.common.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PeliculaViewModel @Inject constructor(
    private val getMovieUseCase: GetMovieUseCase,
    private val stringProvider: StringProvider
) : ViewModel() {
    private val _uiState = MutableLiveData(PeliculaState())
    val uiState: LiveData<PeliculaState> = _uiState

    fun handleEvent(event: PeliculaEvent) {
        when (event) {
            is PeliculaEvent.GetMovie -> getMovie(event.id)
            is PeliculaEvent.AvisoVisto  -> avisoMostrado()
        }
    }

    private fun getMovie(id: Int) {
        Timber.d(Constantes.OBTENIENDO_PELICULA)
        viewModelScope.launch {
            when (val result = getMovieUseCase(id)) {
                is NetworkResult.Success -> {
                    Timber.d(Constantes.PELICULA_OBTENIDA_EXITOSAMENTE)
                    _uiState.value = _uiState.value?.copy(
                        movie = result.data ?: Movie(),
                    )
                }
                is NetworkResult.Error -> {
                    Timber.i(Constantes.ERROR_OBTENIENDO_PELICULA, result.message)
                    _uiState.value = _uiState.value?.copy(
                        aviso = UiEvent.ShowSnackbar(stringProvider.getString(R.string.error_obteniendo_pelicula)),
                    )
                }
            }
        }
    }

    private fun avisoMostrado() {
        _uiState.value = _uiState.value?.copy(aviso = null)
    }
}