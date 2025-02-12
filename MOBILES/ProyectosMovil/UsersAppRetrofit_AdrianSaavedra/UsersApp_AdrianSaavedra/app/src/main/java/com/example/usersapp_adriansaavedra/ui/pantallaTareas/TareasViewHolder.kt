package com.example.usersapp_adriansaavedra.ui.pantallaTareas

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.usersapp_adriansaavedra.databinding.ItemTaskBinding
import com.example.usersapp_adriansaavedra.domain.modelo.Task

class TareasViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemTaskBinding.bind(view)
    fun render(task: Task) {
        with(binding) {
            tvUserName.text = task.userId.toString()
            tvValue.text = task.id.toString()
            tvNombre.text = task.title
            cbTerms.isChecked = task.completed
        }
    }
}