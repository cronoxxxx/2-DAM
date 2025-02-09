package com.example.usersapp_adriansaavedra.ui.pantallaUsuario

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.usersapp_adriansaavedra.databinding.ActivityDetalleFragmentBinding
import com.example.usersapp_adriansaavedra.domain.modelo.User
import com.example.usersapp_adriansaavedra.ui.common.UiEvent
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetalleFragment : Fragment() {
    private var _binding: ActivityDetalleFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetalleViewModel by viewModels()
    private val args: DetalleFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ActivityDetalleFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setupListeners()
        viewModel.handleEvent(DetalleEvent.GetUser(args.userId))
    }

    private fun observeViewModel() {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            pintarUsuario(state.user)
            handleUiEvent(state.aviso)
        }
    }

    private fun handleUiEvent(event: UiEvent?) {
        event?.let {
            when (it) {
                is UiEvent.ShowSnackbar -> {
                    Snackbar.make(binding.root, it.message, Snackbar.LENGTH_LONG).show()
                    viewModel.handleEvent(DetalleEvent.AvisoVisto)
                }
                is UiEvent.PopBackStack -> {
                    findNavController().popBackStack()
                }
            }
        }
    }

    private fun pintarUsuario(user: User) {
        with(binding) {
            etNombre.setText(user.name)
            etEmail.setText(user.email)
            etUsername.setText(user.username)
            etStreet.setText(user.street)
            etCity.setText(user.city)
            etPhone.setText(user.phone)
        }
    }

    private fun setupListeners() {
        binding.updateButton.setOnClickListener {
            val updatedUser = getUserUI()
            viewModel.handleEvent(DetalleEvent.UpdateUser(args.userId, updatedUser))
        }

        binding.deleteButton.setOnClickListener {
            viewModel.handleEvent(DetalleEvent.DeleteUser(args.userId))
        }
    }

    private fun getUserUI() =
        User(
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