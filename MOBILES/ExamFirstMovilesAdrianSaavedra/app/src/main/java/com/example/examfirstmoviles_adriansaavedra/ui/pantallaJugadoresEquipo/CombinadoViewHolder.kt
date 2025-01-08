package com.example.examfirstmoviles_adriansaavedra.ui.pantallaJugadoresEquipo

import android.view.View
import androidx.recyclerview.widget.RecyclerView

import com.example.examfirstmoviles_adriansaavedra.databinding.ItemJugadorBinding
import com.example.examfirstmoviles_adriansaavedra.domain.modelo.Equipo
import com.example.examfirstmoviles_adriansaavedra.domain.modelo.Jugador

class CombinadoViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemJugadorBinding.bind(view)
    fun render(jugador: Jugador) {
        with(binding) {
            tvNombre.text = jugador.nombre
            tvApellido.text = jugador.apellido
            tvDorsal.text = jugador.dorsal.toString()
        }
    }
}