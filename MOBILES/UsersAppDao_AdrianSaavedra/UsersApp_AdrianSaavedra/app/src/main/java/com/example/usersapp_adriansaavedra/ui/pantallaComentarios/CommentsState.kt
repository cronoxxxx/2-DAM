package com.example.usersapp_adriansaavedra.ui.pantallaComentarios

import com.example.usersapp_adriansaavedra.domain.modelo.Comment
import com.example.usersapp_adriansaavedra.ui.common.UiEvent

data class CommentsState(
    val comments: List<Comment> = emptyList(),
    val aviso: UiEvent? = null,
    val isLoading: Boolean = false
)