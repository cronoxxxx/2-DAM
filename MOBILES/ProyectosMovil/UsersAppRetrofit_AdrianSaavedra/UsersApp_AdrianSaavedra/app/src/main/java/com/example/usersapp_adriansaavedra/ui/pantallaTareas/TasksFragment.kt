package com.example.usersapp_adriansaavedra.ui.pantallaTareas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.usersapp_adriansaavedra.R
import com.example.usersapp_adriansaavedra.databinding.ActivityTasksFragmentBinding
import com.example.usersapp_adriansaavedra.ui.common.MarginItemDecoration
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


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
        viewModel.handleEvent(TasksEvent.GetTasks)
    }

    private fun setupRecyclerView() {
        tareasAdapter = TareasAdapter()
        binding.rvTasks.apply {
            adapter = tareasAdapter
            addItemDecoration(MarginItemDecoration(resources.getDimensionPixelSize(R.dimen.margin)))
        }
    }

    private fun observeViewModel() {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            tareasAdapter.submitList(state.tasks)
            state.aviso?.let { showMessage(it) }
        }
    }


    private fun showMessage(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
        viewModel.handleEvent(TasksEvent.AvisoVisto)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}