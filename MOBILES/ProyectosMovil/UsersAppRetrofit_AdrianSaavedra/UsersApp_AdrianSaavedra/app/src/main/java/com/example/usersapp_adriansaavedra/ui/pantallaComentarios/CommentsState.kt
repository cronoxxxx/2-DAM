package com.example.usersapp_adriansaavedra.ui.pantallaComentarios

import com.example.usersapp_adriansaavedra.domain.modelo.Comment

data class CommentsState (
    val comments : List<Comment> = emptyList(),
    val aviso : String? = null,
)