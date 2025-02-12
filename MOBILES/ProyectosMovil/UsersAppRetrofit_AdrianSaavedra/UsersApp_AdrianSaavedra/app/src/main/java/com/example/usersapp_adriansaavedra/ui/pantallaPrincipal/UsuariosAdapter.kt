package com.example.usersapp_adriansaavedra.ui.pantallaPrincipal
//cambiar tipo de dato, nada de data,
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.usersapp_adriansaavedra.R
import com.example.usersapp_adriansaavedra.domain.modelo.User

class UsuariosAdapter(private val onclickBoton: (Int) -> Unit) :
    ListAdapter<User, UsuariosViewHolder>(UsersDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        UsuariosViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false))


    override fun onBindViewHolder(holder: UsuariosViewHolder, position: Int) {
        holder.render(getItem(position), onclickBoton)
    }
}

class UsersDiffCallBack : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: User, newItem: User)= oldItem == newItem
}