package com.example.usersapp_adriansaavedra.ui.pantallaTareas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.usersapp_adriansaavedra.R
import com.example.usersapp_adriansaavedra.databinding.ActivityTasksFragmentBinding
import com.example.usersapp_adriansaavedra.ui.Constantes
import com.example.usersapp_adriansaavedra.ui.common.MarginItemDecoration
import com.example.usersapp_adriansaavedra.ui.common.UiEvent
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class TasksFragment : Fragment() {
    private var _binding: ActivityTasksFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TasksViewModel by viewModels()
    private lateinit var tareasAdapter: TareasAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ActivityTasksFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeViewModel()
        activity?.intent?.getIntExtra(Constantes.ACCEDER, -1)?.let { id ->
            if (id != -1) {
                viewModel.handleEvent(TasksEvent.GetTasks(id))
            }
        }
    }

    private fun setupRecyclerView() {
        tareasAdapter = TareasAdapter()
        binding.rvTasks.apply {
            adapter = tareasAdapter
            addItemDecoration(MarginItemDecoration(resources.getDimensionPixelSize(R.dimen.margin)))
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    tareasAdapter.submitList(state.tasks)
                    handleUiEvent(state.aviso)
                }
            }
        }
    }

    private fun handleUiEvent(event: UiEvent?) {
        if (event is UiEvent.ShowSnackbar) {
            Snackbar.make(binding.root, event.message, Snackbar.LENGTH_LONG).show()
            viewModel.handleEvent(TasksEvent.AvisoVisto)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}