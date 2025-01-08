package com.example.usersapp_adriansaavedra.domain.usecases.comentarios

import com.example.usersapp_adriansaavedra.data.CommentRepository

import javax.inject.Inject

class GetCommentsUseCase @Inject constructor(private val commentRepository: CommentRepository) {
    suspend operator fun invoke(postId: Int) = commentRepository.fetchComments(postId)
}