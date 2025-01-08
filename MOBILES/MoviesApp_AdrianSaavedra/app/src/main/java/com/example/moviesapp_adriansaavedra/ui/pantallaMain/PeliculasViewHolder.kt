package com.example.moviesapp_adriansaavedra.ui.pantallaMain

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.moviesapp_adriansaavedra.BuildConfig
import com.example.moviesapp_adriansaavedra.R
import com.example.moviesapp_adriansaavedra.databinding.ItemPeliculaBinding
import com.example.moviesapp_adriansaavedra.domain.modelo.Movie

class PeliculasViewHolder (view:View) : RecyclerView.ViewHolder(view) {
private val binding = ItemPeliculaBinding.bind(view)
    fun render(movie: Movie, onclickBoton: (Int) -> Unit) {
        with(binding) {
            tvValue.text = movie.id.toString()
            tvNombre.text = movie.title
            ifvImageView.load(BuildConfig.IMAGE_URL + movie.imgPath) {
                placeholder(R.drawable.background_redondo)
                error(R.drawable.ic_launcher_foreground)
                transformations(RoundedCornersTransformation(12f))
                size(200, 200)
                crossfade(true)
            }
            btnAcceso.setOnClickListener { onclickBoton(movie.id) }
        }
    }
}