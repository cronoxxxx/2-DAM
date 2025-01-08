package com.example.examfirstmoviles_adriansaavedra.ui.pantallaMomentos

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
import com.example.examfirstmoviles_adriansaavedra.R
import com.example.examfirstmoviles_adriansaavedra.databinding.ActivityMainFragmentBinding
import com.example.examfirstmoviles_adriansaavedra.databinding.ActivityMomentoFragmentBinding
import com.example.examfirstmoviles_adriansaavedra.ui.common.MarginItemDecoration
import com.example.examfirstmoviles_adriansaavedra.ui.pantallaPrincipal.EquiposAdapter
import com.example.examfirstmoviles_adriansaavedra.ui.pantallaPrincipal.MainViewModel
import com.example.examfirstmoviles_adriansaavedra.ui.pantallaPrincipal.MomentosEvent
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MomentosFragment : Fragment() {
    private var _binding: ActivityMomentoFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var usersAdapter: MomentosAdapter
    private val viewModel: MomentosViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ActivityMomentoFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeViewModel()
        viewModel.handleEvent(MomentosEvent.GetEquipos)
    }

    private fun setupRecyclerView() {
        usersAdapter = MomentosAdapter()
        binding.rvUsers.apply {
            adapter = usersAdapter
            addItemDecoration(MarginItemDecoration(16))
        }

    }

    private fun observeViewModel() {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            usersAdapter.submitList(state.equipos)
            state.aviso?.let { showMessage(it) }
        }
    }


    private fun showMessage(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
        viewModel.handleEvent(MomentosEvent.AvisoVisto)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}