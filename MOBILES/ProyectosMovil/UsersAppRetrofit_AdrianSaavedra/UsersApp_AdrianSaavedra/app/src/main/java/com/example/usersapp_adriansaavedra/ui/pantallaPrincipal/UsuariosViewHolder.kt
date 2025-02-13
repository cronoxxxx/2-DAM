package com.example.usersapp_adriansaavedra.ui.pantallaPrincipal

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.usersapp_adriansaavedra.BuildConfig
import com.example.usersapp_adriansaavedra.databinding.ItemUserBinding
import com.example.usersapp_adriansaavedra.domain.modelo.User

class UsuariosViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemUserBinding.bind(view)
    fun render(user: User, onclickBoton: (Int) -> Unit) {
        with(binding) {
            tvValue.text = user.id.toString()
            tvNombre.text = user.name
            ifvImageView.load(BuildConfig.IMAGE_DIR)
            btnAcceso.setOnClickListener { onclickBoton(user.id) }
        }
    }
}