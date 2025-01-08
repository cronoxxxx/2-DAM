package com.example.usersapp_adriansaavedra.ui.pantallaComentarios

sealed class CommentsEvent {
    data object GetComments: CommentsEvent()
    data object AvisoVisto : CommentsEvent()
}