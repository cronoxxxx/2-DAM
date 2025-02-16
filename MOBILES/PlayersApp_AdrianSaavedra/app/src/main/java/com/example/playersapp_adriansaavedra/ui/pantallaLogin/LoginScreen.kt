package com.example.playersapp_adriansaavedra.ui.pantallaLogin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.playersapp_adriansaavedra.data.remote.model.Login
import com.example.playersapp_adriansaavedra.data.remote.model.Register
import com.example.playersapp_adriansaavedra.ui.common.UiEvent

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
        LoginScreenParams(
            state = state,
            onUsernameLoginChange = { viewModel.handleEvent(LoginEvent.OnUsernameLoginChange(it)) },
            onPasswordLoginChange = { viewModel.handleEvent(LoginEvent.OnPasswordLoginChange(it)) },
            onEmailPasswordChange = { viewModel.handleEvent(LoginEvent.OnEmailRegisterChange(it)) },
            onUsernameRegisterChange = { viewModel.handleEvent(LoginEvent.OnUsernameRegisterChange(it)) },
            onPasswordRegisterChange = { viewModel.handleEvent(LoginEvent.OnPasswordRegisterChange(it)) },
            onLoginClick = { viewModel.handleEvent(LoginEvent.OnLoginClick(state.login.username, state.login.password)) },
            onRegisterClick = { viewModel.handleEvent(LoginEvent.OnRegisterClick(state.register.username, state.register.password, state.register.email)) }
        )
    )
}

@Composable
fun LoginScreenContent(
    params: LoginScreenParams
) {
    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Register form
            Text("Register", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = params.state.register.username,
                onValueChange = params.onUsernameRegisterChange,
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = params.state.register.password,
                onValueChange = params.onPasswordRegisterChange,
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = params.state.register.email,
                onValueChange = params.onEmailPasswordChange,
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = params.onRegisterClick,
                modifier = Modifier.fillMaxWidth(),
                enabled = !params.state.isLoading
            ) {
                Text("Register")
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Login form
            Text("Login", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = params.state.login.username,
                onValueChange = params.onUsernameLoginChange,
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = params.state.login.password,
                onValueChange = params.onPasswordLoginChange,
                label = { Text("Password") },

                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = params.onLoginClick,
                modifier = Modifier.fillMaxWidth(),
                enabled = !params.state.isLoading
            ) {
                Text("Login")
            }
        }

        if (params.state.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

data class LoginScreenParams(
    val state: LoginState,
    val onUsernameLoginChange: (String) -> Unit = {},
    val onPasswordLoginChange: (String) -> Unit = {},
    val onUsernameRegisterChange: (String) -> Unit = {},
    val onPasswordRegisterChange: (String) -> Unit = {},
    val onEmailPasswordChange: (String) -> Unit = {},
    val onLoginClick: () -> Unit = {},
    val onRegisterClick: () -> Unit = {}
)

class LoginStateProvider: PreviewParameterProvider<LoginState> {
    override val values = sequenceOf(
        LoginState(
            login = Login("johndoe", "password123"),
            register = Register("johndoe", "password123", "john@example.com"),
            isLoading = true
        ),
        LoginState(
            login = Login("johndoe", "password123"),
            register = Register("johndoe", "password123", "john@example.com"),
            isLoading = false
        )
    )
}

@Preview(showBackground = true, device = Devices.PHONE)
@Composable
fun LoginScreenPreview(@PreviewParameter(LoginStateProvider::class) state: LoginState) {
    LoginScreenContent(
        LoginScreenParams(state = state)
    )
}