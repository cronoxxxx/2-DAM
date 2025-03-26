package com.example.finalexamenmoviles_adriansaavedra.ui.pantallaLogin

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*

import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.finalexamenmoviles_adriansaavedra.ui.common.UiEvent

@Composable
fun LoginScreen(
    navigateToHome: () -> Unit,
    showSnackbar: (String) -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.aviso) {
        state.aviso?.let {
            when (it) {
                is UiEvent.ShowSnackbar -> {
                    showSnackbar(it.message)
                    viewModel.handleEvent(LoginEvent.OnAvisoVisto)
                }
                is UiEvent.Navigate -> {
                    navigateToHome()
                    viewModel.handleEvent(LoginEvent.OnAvisoVisto)
                }
            }
        }
    }

    LoginScreenContent(
        state = state,
        onUsernameChange = { viewModel.handleEvent(LoginEvent.OnUsernameChange(it)) },
        onPasswordChange = { viewModel.handleEvent(LoginEvent.OnPasswordChange(it)) },
        onLoginClick = { viewModel.handleEvent(LoginEvent.OnLoginClick) }
    )
}

@Composable
fun LoginScreenContent(
    state: LoginState,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit
) {
    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Login",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            OutlinedTextField(
                value = state.username,
                onValueChange = onUsernameChange,
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = state.password,
                onValueChange = onPasswordChange,
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onLoginClick,
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.isLoading
            ) {
                Text("Login")
            }

            if (state.isLoading) {
                Spacer(modifier = Modifier.height(16.dp))
                CircularProgressIndicator()
            }
        }
    }
}