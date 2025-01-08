package com.example.usersapp_adriansaavedra.ui.pantallaComentarios

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.usersapp_adriansaavedra.R
import com.example.usersapp_adriansaavedra.domain.modelo.Comment

class ComentariosAdapter : ListAdapter<Comment, ComentariosViewHolder>(CommentsDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ComentariosViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false)
        )

    override fun onBindViewHolder(holder: ComentariosViewHolder, position: Int) {
        holder.render(getItem(position))
    }
}

class CommentsDiffCallBack : DiffUtil.ItemCallback<Comment>() {
    override fun areItemsTheSame(oldItem: Comment, newItem: Comment) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Comment, newItem: Comment) = oldItem == newItem
}