package com.example.usersapp_adriansaavedra.ui.pantallaTareas

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.usersapp_adriansaavedra.R
import com.example.usersapp_adriansaavedra.domain.modelo.Task

class TareasAdapter : ListAdapter<Task, TareasViewHolder>(TasksDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TareasViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
    )

    override fun onBindViewHolder(holder: TareasViewHolder, position: Int) {
        holder.render(getItem(position))
    }
}

class TasksDiffCallBack : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Task, newItem: Task) = oldItem == newItem
}