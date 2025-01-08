package com.example.examfirstmoviles_adriansaavedra.ui.pantallaPrincipal

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.examfirstmoviles_adriansaavedra.databinding.ItemGrupoBinding
import com.example.examfirstmoviles_adriansaavedra.domain.modelo.Equipo

class EquiposViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemGrupoBinding.bind(view)
    fun render(equipo: Equipo,onclickBoton: (Int) -> Unit) {
        with(binding) {
            tvNombre    .text = equipo.id.toString()
            tvApellido.text = equipo.nombre
            btnAcceso.setOnClickListener{onclickBoton(equipo.id)}
        }
    }
}