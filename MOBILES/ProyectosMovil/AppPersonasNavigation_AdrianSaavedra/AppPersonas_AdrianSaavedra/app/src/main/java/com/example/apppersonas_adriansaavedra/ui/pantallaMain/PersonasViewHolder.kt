package com.example.apppersonas_adriansaavedra.ui.pantallaMain

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.apppersonas_adriansaavedra.databinding.ItemPersonaBinding
import com.example.apppersonas_adriansaavedra.domain.modelo.Persona
import com.example.apppersonas_adriansaavedra.ui.Constantes

class PersonasViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemPersonaBinding.bind(view)
    fun render(persona: Persona, onClickBoton: (Int) -> Unit) {
        with(binding) {
            ifvImageView.load(Constantes.IMG_STEVE) {
                transformations(RoundedCornersTransformation(12f))
            }
            tvNombre.text = persona.nombre
            tvId.text = persona.id.toString()
            btnAcceso.setOnClickListener { onClickBoton(persona.id) }
        }
    }
}
