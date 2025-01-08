package com.example.usersapp_adriansaavedra.ui.pantallaAgregar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.usersapp_adriansaavedra.databinding.ActivityAddUsuarioFragmentBinding
import com.example.usersapp_adriansaavedra.domain.modelo.User
import com.example.usersapp_adriansaavedra.ui.common.UiEvent
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddUsuarioFragment : Fragment() {
    private var _binding: ActivityAddUsuarioFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AddUsuarioViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = ActivityAddUsuarioFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupEventListeners()
        observeViewModel()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    state.aviso?.let { event ->
                        when (event) {
                            is UiEvent.ShowSnackbar -> {
                                Snackbar.make(binding.root, event.message, Snackbar.LENGTH_SHORT).show()
                            }
                            is UiEvent.PopBackStack -> {
                                findNavController().popBackStack()
                            }
                        }
                        viewModel.handleEvent(AddUsuarioEvent.AvisoVisto)
                    }
                }

            }
        }
    }

    private fun setupEventListeners() {
        binding.updateButton.setOnClickListener {
            val newUser = createUserFromUI()
            viewModel.handleEvent(AddUsuarioEvent.AddUser(newUser))
        }
    }

    private fun createUserFromUI()= User(
            name = binding.etNombre.text.toString(),
            email = binding.etEmail.text.toString(),
            username = binding.etUsername.text.toString(),
            street = binding.etStreet.text.toString(),
            city = binding.etCity.text.toString(),
            phone = binding.etPhone.text.toString(),
        )


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}