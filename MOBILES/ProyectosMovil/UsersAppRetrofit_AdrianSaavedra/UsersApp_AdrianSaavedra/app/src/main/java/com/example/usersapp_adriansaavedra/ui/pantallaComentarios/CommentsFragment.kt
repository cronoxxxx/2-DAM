package com.example.usersapp_adriansaavedra.ui.pantallaComentarios

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.usersapp_adriansaavedra.R
import com.example.usersapp_adriansaavedra.databinding.ActivityCommentsFragmentBinding
import com.example.usersapp_adriansaavedra.ui.common.MarginItemDecoration
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

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
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            comentariosAdapter.submitList(state.comments)
            state.aviso?.let { showMessage(it) }
        }
    }


    private fun showMessage(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
        viewModel.handleEvent(CommentsEvent.AvisoVisto)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}