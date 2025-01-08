package com.example.examfirstmoviles_adriansaavedra.ui.pantallaPrincipal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController

import com.example.examfirstmoviles_adriansaavedra.databinding.ActivityMainFragmentBinding
import com.example.examfirstmoviles_adriansaavedra.ui.common.MarginItemDecoration
import com.example.examfirstmoviles_adriansaavedra.ui.pantallaMomentos.MainEvent
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {
    private var _binding: ActivityMainFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var usersAdapter: EquiposAdapter
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ActivityMainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeViewModel()
        viewModel.handleEvent(MainEvent.GetEquipos)
    }

    private fun setupRecyclerView() {
        usersAdapter = EquiposAdapter(::click)
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
        viewModel.handleEvent(MainEvent.AvisoVisto)
    }

    private fun click(id: Int) {
        val action = MainFragmentDirections.actionPlayersFragmentToCombinadoFragment(id)
        binding.root.findNavController().navigate(action)
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}