package com.example.examfirstmoviles_adriansaavedra.ui.pantallaJugadoresEquipo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.examfirstmoviles_adriansaavedra.R
import com.example.examfirstmoviles_adriansaavedra.databinding.ActivityCombinadoFragmentBinding
import com.example.examfirstmoviles_adriansaavedra.databinding.ActivityMomentoFragmentBinding
import com.example.examfirstmoviles_adriansaavedra.ui.common.MarginItemDecoration
import com.example.examfirstmoviles_adriansaavedra.ui.common.StringProvider
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CombinadoFragment : Fragment() {
    private var _binding: ActivityCombinadoFragmentBinding? = null
    private val binding get() = _binding!!
 private val viewModel: CombinadoViewModel by viewModels()
    private val args : CombinadoFragmentArgs by navArgs()
    private lateinit var  combinadoAdapter : CombinadoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ActivityCombinadoFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun showMessage(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
        viewModel.handleEvent(CombinadoEvent.AvisoVisto)
    }




    private fun observeViewModel() {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            combinadoAdapter.submitList(state.players)
            state.aviso?.let { showMessage(it) }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeViewModel()
        viewModel.handleEvent(CombinadoEvent.GetPlayers(args.grupoId))
    }


    private fun setupRecyclerView() {
        combinadoAdapter = CombinadoAdapter()
        binding.rvUsers.apply {
            adapter = combinadoAdapter
            addItemDecoration(MarginItemDecoration(resources.getDimensionPixelSize(R.dimen.margin)))
        }

    }
}