package com.example.usersapp_adriansaavedra.ui.pantallaComentarios

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
import com.example.usersapp_adriansaavedra.databinding.ActivityCommentsFragmentBinding
import com.example.usersapp_adriansaavedra.ui.common.MarginItemDecoration
import com.example.usersapp_adriansaavedra.ui.common.UiEvent
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CommentsFragment :  Fragment() {
    private var _binding : ActivityCommentsFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel:CommentsViewModel by viewModels()
    private lateinit var comentariosAdapter: ComentariosAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ActivityCommentsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeViewModel()
        viewModel.handleEvent(CommentsEvent.GetComments)
    }

    private fun setupRecyclerView() {
        comentariosAdapter = ComentariosAdapter()
        binding.rvCommentsGroup.apply {
            adapter = comentariosAdapter
            addItemDecoration(MarginItemDecoration(resources.getDimensionPixelSize(R.dimen.margin)))
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    comentariosAdapter.submitList(state.comments)
                    handleUiEvent(state.aviso)
                }
            }
        }
    }

    private fun handleUiEvent(event: UiEvent?) {
        if (event is UiEvent.ShowSnackbar) {
            Snackbar.make(binding.root, event.message, Snackbar.LENGTH_LONG).show()
            viewModel.handleEvent(CommentsEvent.AvisoVisto)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}