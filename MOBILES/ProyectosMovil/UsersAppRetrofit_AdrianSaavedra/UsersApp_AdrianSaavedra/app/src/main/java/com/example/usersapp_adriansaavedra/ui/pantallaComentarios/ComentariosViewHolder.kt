package com.example.usersapp_adriansaavedra.ui.pantallaComentarios

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.usersapp_adriansaavedra.databinding.ItemCommentBinding
import com.example.usersapp_adriansaavedra.domain.modelo.Comment

class ComentariosViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemCommentBinding.bind(view)
    fun render(comment: Comment) {
        with(binding) {
            tvId.text = comment.postId.toString()
            tvComment.text = comment.body
            tvValue.text = comment.id.toString()
            tvEmail.text = comment.email
        }
    }
}