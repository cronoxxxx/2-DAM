package com.example.moviesapp_adriansaavedra.ui.pantallaMain

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.moviesapp_adriansaavedra.R
import com.example.moviesapp_adriansaavedra.domain.modelo.Movie

class MoviesAdapter (private val onclickBoton: (Int) -> Unit) : ListAdapter<Movie, PeliculasViewHolder>(MoviesDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeliculasViewHolder {
        return PeliculasViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_pelicula, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PeliculasViewHolder, position: Int) {
        holder.render(getItem(position), onclickBoton)
    }
}

class MoviesDiffCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }

}