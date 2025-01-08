package com.example.apppersonas_adriansaavedra.ui.pantallaMain

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.apppersonas_adriansaavedra.R
import com.example.apppersonas_adriansaavedra.domain.modelo.Persona

class PersonasAdapter(
    private val onClickBoton: (Int) -> Unit
) : ListAdapter<Persona, PersonasViewHolder>(PersonaDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonasViewHolder {
        val layoutInflador = LayoutInflater.from(parent.context)
        return PersonasViewHolder(layoutInflador.inflate(R.layout.item_persona, parent, false))
    }

    override fun onBindViewHolder(holder: PersonasViewHolder, position: Int) {
        holder.render(getItem(position), onClickBoton)
    }
}

class PersonaDiffCallback : DiffUtil.ItemCallback<Persona>() {
    override fun areItemsTheSame(oldItem: Persona, newItem: Persona): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Persona, newItem: Persona): Boolean {
        return oldItem == newItem
    }
}
