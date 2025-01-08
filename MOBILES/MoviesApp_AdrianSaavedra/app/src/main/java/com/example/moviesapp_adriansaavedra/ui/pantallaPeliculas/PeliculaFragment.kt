package com.example.moviesapp_adriansaavedra.ui.pantallaPeliculas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.moviesapp_adriansaavedra.BuildConfig
import com.example.moviesapp_adriansaavedra.R
import com.example.moviesapp_adriansaavedra.databinding.FragmentPeliculaBinding
import com.example.moviesapp_adriansaavedra.domain.modelo.Movie
import com.example.moviesapp_adriansaavedra.ui.Constantes
import com.example.moviesapp_adriansaavedra.ui.common.UiEvent
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import kotlin.math.roundToInt

@AndroidEntryPoint
class PeliculaFragment : Fragment() {

    private var _binding: FragmentPeliculaBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PeliculaViewModel by viewModels()
    private val args: PeliculaFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPeliculaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d(Constantes.ACCEDIENDO_FRAGMENTO_PELICULA, args.peliculaId)
        observarViewModel()
        viewModel.handleEvent(PeliculaEvent.GetMovie(args.peliculaId))
    }

    private fun observarViewModel() {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            pintarPelicula(state.movie)
            handleUiEvent(state.aviso)
        }
    }

    private fun handleUiEvent(event: UiEvent?) {
        event?.let {
            when (it) {
                is UiEvent.ShowSnackbar -> {
                    Snackbar.make(binding.root, it.message, Snackbar.LENGTH_LONG).show()
                    viewModel.handleEvent(PeliculaEvent.AvisoVisto)
                }
            }
        }
    }


    private fun pintarPelicula(movie: Movie) {
        Timber.d(Constantes.PELICULA_PINTADA_EXITOSAMENTE)
        with(binding) {
            etNombre.setText(movie.title)
            etOriginalTitle.setText(movie.originalTitle)
            etOverview.setText(movie.overview)
            val roundedValue = (movie.voteAverage * 10).roundToInt() / 10f
            slRate.value = roundedValue
            etReleaseDate.setText(movie.releaseDate)
            ifvImageView.load(BuildConfig.IMAGE_URL + movie.imgPath) {
                placeholder(R.drawable.background_redondo)
                error(R.drawable.ic_launcher_foreground)
                transformations(RoundedCornersTransformation(12f))
                size(200, 200)
                crossfade(true)
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}