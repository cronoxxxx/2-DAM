package com.example.usersapp_adriansaavedra.ui.pantallaComentarios

sealed class CommentsEvent {
    data class GetComments(val id: Int) : CommentsEvent()
    data object AvisoVisto : CommentsEvent()
}