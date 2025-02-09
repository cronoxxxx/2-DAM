package com.example.consolesapp_adriansaavedra.ui.pantallaLogin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.consolesapp_adriansaavedra.R
import com.example.consolesapp_adriansaavedra.domain.model.Player
import com.example.consolesapp_adriansaavedra.ui.common.UiEvent
import com.example.consolesapp_adriansaavedra.ui.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            MaterialTheme {
                Navigation()
            }
        }
    }
}


@Composable
fun LoginScreen(
    navigateToHome: (Int) -> Unit,
    showSnackbar: (String) -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.aviso) {
        state.aviso?.let {
            when (it) {
                is UiEvent.ShowSnackbar -> {
                    showSnackbar(it.message)
                    viewModel.handleEvent(LoginEvent.AvisoVisto)
                }

                is UiEvent.Navigate -> {
                    navigateToHome(state.idLogin.toInt())
                    viewModel.handleEvent(LoginEvent.AvisoVisto)
                }
            }
        }
    }

    LoginScreenContent(
        state = state,
        onUsernameChange = { viewModel.handleEvent(LoginEvent.OnUsernameChange(it)) },
        onPasswordChange = { viewModel.handleEvent(LoginEvent.OnPasswordChange(it)) },
        onLoginClick = {
            viewModel.handleEvent(
                LoginEvent.OnLoginClick(
                    Player(
                        username = state.username,
                        password = state.password
                    )
                )
            )
        },
        onRegisterClick = {
            viewModel.handleEvent(
                LoginEvent.OnRegisterClick(
                    Player(
                        username = state.username,
                        password = state.password
                    )
                )
            )
        }
    )
}

@Composable
fun LoginScreenContent(
    state: LoginState,
    onUsernameChange: (String) -> Unit = {},
    onPasswordChange: (String) -> Unit = {},
    onLoginClick: () -> Unit = {},
    onRegisterClick: () -> Unit = {}
) {
    Surface {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
            ) {
                OutlinedTextField(
                    value = state.username,
                    onValueChange = onUsernameChange,
                    label = { Text(stringResource(id = R.string.username_label)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = dimensionResource(id = R.dimen.padding_medium))
                )

                OutlinedTextField(
                    value = state.password,
                    onValueChange = onPasswordChange,
                    label = { Text(stringResource(id = R.string.password_label)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom=dimensionResource(id = R.dimen.padding_medium))
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = onLoginClick,
                        enabled = !state.isLoading
                    ) {
                        if (state.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(dimensionResource(id = R.dimen.progress_indicator_size)),
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        } else {
                            Text(stringResource(id = R.string.login_button))
                        }
                    }
                    Button(
                        onClick = onRegisterClick,
                        enabled = !state.isLoading
                    ) {
                        if (state.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(dimensionResource(id = R.dimen.progress_indicator_size)),
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        } else {
                            Text(stringResource(id = R.string.register_button))
                        }
                    }
                }
            }
        }
    }
}

class LoginStateProvider : PreviewParameterProvider<LoginState> {
    override val values = sequenceOf(
        LoginState(username = "", password = "", isLoading = false),
        LoginState(username = "user123", password = "password", isLoading = false),
        LoginState(username = "user123", password = "password", isLoading = true)
    )
}

@Preview(showSystemUi = true, showBackground = true, device = Devices.PHONE)
@Composable
fun LoginScreenPreview(@PreviewParameter(LoginStateProvider::class) state: LoginState) {
    LoginScreenContent(state = state)
}



