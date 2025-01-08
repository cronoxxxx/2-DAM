package com.example.examfirstmoviles_adriansaavedra.ui.pantallaJugadoresEquipo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.examfirstmoviles_adriansaavedra.R
import com.example.examfirstmoviles_adriansaavedra.domain.modelo.Equipo
import com.example.examfirstmoviles_adriansaavedra.domain.modelo.Jugador
import com.example.examfirstmoviles_adriansaavedra.ui.pantallaMomentos.MomentosViewHolder

class CombinadoAdapter (): ListAdapter<Jugador, CombinadoViewHolder>(TasksDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CombinadoViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_jugador, parent, false)
    )
    override fun onBindViewHolder(holder: CombinadoViewHolder, position: Int) {
        holder.render(getItem(position))
    }

}

class TasksDiffCallBack : DiffUtil.ItemCallback<Jugador>() {
    override fun areItemsTheSame(oldItem: Jugador, newItem: Jugador) = oldItem.nombre == newItem.nombre
    override fun areContentsTheSame(oldItem: Jugador, newItem: Jugador) = oldItem == newItem
}