package com.example.moviesapp_adriansaavedra.ui.pantallaMain

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.moviesapp_adriansaavedra.ui.common.MarginItemDecoration
import com.example.moviesapp_adriansaavedra.databinding.FragmentMainBinding
import com.example.moviesapp_adriansaavedra.ui.Constantes
import com.example.moviesapp_adriansaavedra.ui.common.UiEvent
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainFragment : Fragment() {
    private var _binding : FragmentMainBinding ? = null
    private val binding get() = _binding!!
    private lateinit var moviesAdapter: MoviesAdapter
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }


    private fun observarViewModel() {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            moviesAdapter.submitList(state.movies)
            handleUiEvent(state.aviso)
        }
    }

    private fun handleUiEvent(event: UiEvent?) {
        event?.let {
            when (it) {
                is UiEvent.ShowSnackbar -> {
                    Snackbar.make(binding.root, it.message, Snackbar.LENGTH_SHORT).show()
                    viewModel.handleEvent(MainEvent.AvisoVisto)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d(Constantes.ACCEDIENDO_AL_LISTADO_DE_PELICULAS)
        moviesAdapter = MoviesAdapter(::click)
        binding.rvPersonas.apply {
            adapter = moviesAdapter
            addItemDecoration(MarginItemDecoration(16))
        }




        observarViewModel()
        viewModel.handleEvent(MainEvent.GetMovies)
    }

    private fun click(id: Int){
        val action = MainFragmentDirections.actionMainFragmentToPeliculaFragment(id)
        binding.root.findNavController().navigate(action)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}