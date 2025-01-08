package com.example.usersapp_adriansaavedra.ui.pantallaComentarios

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.usersapp_adriansaavedra.R
import com.example.usersapp_adriansaavedra.data.remote.NetworkResult
import com.example.usersapp_adriansaavedra.domain.usecases.comentarios.GetCommentsUseCase
import com.example.usersapp_adriansaavedra.ui.common.StringProvider
import com.example.usersapp_adriansaavedra.ui.common.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommentsViewModel @Inject constructor(
    private val stringProvider: StringProvider,
    private val getCommentsUseCase: GetCommentsUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(CommentsState())
    val uiState = _uiState.asStateFlow()

    fun handleEvent(event: CommentsEvent) {
        when (event) {
            is CommentsEvent.GetComments -> getComments(event.id)
            is CommentsEvent.AvisoVisto -> avisoMostrado()
        }
    }

    private fun getComments(id:Int) {
        viewModelScope.launch {
            when (val result = getCommentsUseCase(id)) {
                is NetworkResult.Success -> {
                    _uiState.update { currentState -> currentState.copy(comments = result.data) }
                }

                is NetworkResult.Loading -> {
                    _uiState.update { currentState -> currentState.copy(isLoading = true) }
                }

                is NetworkResult.Error -> {
                    _uiState.update { currentState ->
                        currentState.copy(
                            aviso = UiEvent.ShowSnackbar(
                                stringProvider.getString(R.string.error_obteniendo_comentarios)
                            )
                        )
                    }
                }
            }
        }
    }

    private fun avisoMostrado() {
        _uiState.update { currentState -> currentState.copy(aviso = null) }
    }
}