package com.example.playersapp_adriansaavedra.ui.pantallaLogin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.playersapp_adriansaavedra.R
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
            onEmailRegisterChange = { viewModel.handleEvent(LoginEvent.OnEmailRegisterChange(it)) },
            onUsernameRegisterChange = {
                viewModel.handleEvent(
                    LoginEvent.OnUsernameRegisterChange(
                        it
                    )
                )
            },
            onPasswordRegisterChange = {
                viewModel.handleEvent(
                    LoginEvent.OnPasswordRegisterChange(
                        it
                    )
                )
            },
            onLoginClick = {
                viewModel.handleEvent(
                    LoginEvent.OnLoginClick(
                        state.login.username,
                        state.login.password
                    )
                )
            },
            onRegisterClick = {
                viewModel.handleEvent(
                    LoginEvent.OnRegisterClick(
                        state.register.username,
                        state.register.password,
                        state.register.email
                    )
                )
            }
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
                .padding(dimensionResource(R.dimen.login_input_spacing)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.register_title),
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.login_form_spacing)))
            OutlinedTextField(
                value = params.state.register.username,
                onValueChange = params.onUsernameRegisterChange,
                label = { Text(stringResource(R.string.username_label)) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.login_input_spacing)))
            OutlinedTextField(
                value = params.state.register.password,
                onValueChange = params.onPasswordRegisterChange,
                label = { Text(stringResource(R.string.password_label)) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.login_input_spacing)))
            OutlinedTextField(
                value = params.state.register.email,
                onValueChange = params.onEmailRegisterChange,
                label = { Text(stringResource(R.string.email_label)) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.login_form_spacing)))
            Button(
                onClick = params.onRegisterClick,
                modifier = Modifier.fillMaxWidth(),
                enabled = !params.state.isLoading
            ) {
                Text(stringResource(R.string.register_title))
            }

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.login_section_spacing)))


            Text(stringResource(R.string.login_title), style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.login_input_spacing)))
            OutlinedTextField(
                value = params.state.login.username,
                onValueChange = params.onUsernameLoginChange,
                label = { Text(stringResource(R.string.username_label)) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.login_input_spacing)))
            OutlinedTextField(
                value = params.state.login.password,
                onValueChange = params.onPasswordLoginChange,
                label = { Text(stringResource(R.string.password_label)) },

                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.login_form_spacing)))
            Button(
                onClick = params.onLoginClick,
                modifier = Modifier.fillMaxWidth(),
                enabled = !params.state.isLoading
            ) {
                Text(stringResource(R.string.username_label))
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
    val onEmailRegisterChange: (String) -> Unit = {},
    val onLoginClick: () -> Unit = {},
    val onRegisterClick: () -> Unit = {}
)

class LoginStateProvider : PreviewParameterProvider<LoginState> {
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