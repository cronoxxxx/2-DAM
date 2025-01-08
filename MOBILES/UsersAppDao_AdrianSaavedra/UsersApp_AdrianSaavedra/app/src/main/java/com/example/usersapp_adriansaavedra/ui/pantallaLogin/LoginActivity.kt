package com.example.usersapp_adriansaavedra.ui.pantallaLogin

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.usersapp_adriansaavedra.databinding.ActivityLoginBinding
import com.example.usersapp_adriansaavedra.domain.modelo.User
import com.example.usersapp_adriansaavedra.ui.Constantes
import com.example.usersapp_adriansaavedra.ui.common.UiEvent
import com.example.usersapp_adriansaavedra.ui.pantallaPrincipal.MainActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private val viewModel: LoginViewModel by viewModels()
    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupUI()
        observeViewModel()
    }

    private fun setupUI() {
        binding.loginButton.setOnClickListener {
            val username = binding.usernameInputLayout.editText?.text.toString()
            val password = binding.passwordInputLayout.editText?.text.toString()
            val user = User(username = username, password = password)
            viewModel.handleEvent(LoginEvent.LoginUser(user))
        }

        binding.registerButton.setOnClickListener {
            val username = binding.usernameInputLayout.editText?.text.toString()
            val password = binding.passwordInputLayout.editText?.text.toString()
            val user = User(username = username, password = password)
            viewModel.handleEvent(LoginEvent.RegisterUser(user))
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                handleLoadingState(state.isLoading)

                state.aviso?.let { aviso ->
                    if (aviso is UiEvent.ShowSnackbar) {
                        Snackbar.make(binding.root, aviso.message, Snackbar.LENGTH_SHORT).show()
                        viewModel.handleEvent(LoginEvent.AvisoVisto)
                    }
                }

                if (state.idLogin != -1) {
                    navigateToMainActivity(state.idLogin)
                }
            }
        }
    }

    private fun handleLoadingState(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
        binding.loginButton.isEnabled = !isLoading
        binding.registerButton.isEnabled = !isLoading
        binding.usernameInputLayout.isEnabled = !isLoading
        binding.passwordInputLayout.isEnabled = !isLoading
    }


    private fun navigateToMainActivity(id: Int) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(Constantes.ACCEDER, id)
        startActivity(intent)
    }

}


