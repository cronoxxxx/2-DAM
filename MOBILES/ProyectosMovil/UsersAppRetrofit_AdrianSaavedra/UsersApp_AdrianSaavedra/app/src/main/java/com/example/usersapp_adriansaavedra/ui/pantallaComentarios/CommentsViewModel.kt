package com.example.usersapp_adriansaavedra.ui.pantallaComentarios

import androidx.lifecycle.*
import com.example.usersapp_adriansaavedra.R
import com.example.usersapp_adriansaavedra.data.remote.NetworkResult
import com.example.usersapp_adriansaavedra.domain.usecases.comentarios.GetCommentsUseCase
import com.example.usersapp_adriansaavedra.ui.common.StringProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
class CommentsViewModel @Inject constructor(
    private val stringProvider: StringProvider,
    private val getCommentsUseCase: GetCommentsUseCase
) : ViewModel() {
    private val _uiState = MutableLiveData(CommentsState())
    val uiState : LiveData<CommentsState> = _uiState

    fun handleEvent(event: CommentsEvent) {
        when (event) {
            is CommentsEvent.GetComments -> getComments()
            is CommentsEvent.AvisoVisto -> avisoMostrado()
        }
    }

    private fun getComments() {
        viewModelScope.launch {
            when (val result = getCommentsUseCase()) {
                is NetworkResult.Success -> {
                    _uiState.value = _uiState.value?.copy(
                        comments = result.data
                    )
                }
                is NetworkResult.Error -> {
                    _uiState.value = _uiState.value?.copy(
                        aviso = stringProvider.getString(R.string.error_obteniendo_comentarios)
                    )
                }
            }
        }
    }

    private fun avisoMostrado() {
        _uiState.value = _uiState.value?.copy(aviso = null)
    }
}