package com.example.examfirstmoviles_adriansaavedra.ui.pantallaPrincipal

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.examfirstmoviles_adriansaavedra.R
import com.example.examfirstmoviles_adriansaavedra.domain.modelo.Equipo

class EquiposAdapter (private val onclickBoton: (Int) -> Unit) : ListAdapter<Equipo, EquiposViewHolder>(TasksDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = EquiposViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_grupo, parent, false)
    )

    override fun onBindViewHolder(holder: EquiposViewHolder, position: Int) {
        holder.render(getItem(position),onclickBoton)
    }
}

class TasksDiffCallBack : DiffUtil.ItemCallback<Equipo>() {
    override fun areItemsTheSame(oldItem: Equipo, newItem: Equipo) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Equipo, newItem: Equipo) = oldItem == newItem
}