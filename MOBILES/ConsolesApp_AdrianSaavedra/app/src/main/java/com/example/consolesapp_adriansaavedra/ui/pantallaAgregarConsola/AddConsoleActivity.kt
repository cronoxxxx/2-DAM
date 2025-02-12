package com.example.consolesapp_adriansaavedra.ui.pantallaAgregarConsola

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.consolesapp_adriansaavedra.R
import com.example.consolesapp_adriansaavedra.domain.model.Console
import com.example.consolesapp_adriansaavedra.ui.common.UiEvent

@Composable
fun AddConsoleScreen(
    showSnackbar: (String) -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: AddConsoleViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val userId by viewModel.userId.collectAsStateWithLifecycle()

    LaunchedEffect(userId) {
        viewModel.handleEvent(AddConsoleEvent.UpdateUserId(userId))
    }


    LaunchedEffect(state.aviso) {
        state.aviso?.let {
            when (it) {
                is UiEvent.ShowSnackbar -> {
                    showSnackbar(it.message)
                    viewModel.handleEvent(AddConsoleEvent.AvisoVisto)
                }

                is UiEvent.Navigate -> {
                    onNavigateBack()
                    viewModel.handleEvent(AddConsoleEvent.AvisoVisto)
                }
            }
        }
    }

    AddConsoleScreenContent(
        state = state,
        onNameChange = { viewModel.handleEvent(AddConsoleEvent.OnNameChange(it)) },
        onModelChange = { viewModel.handleEvent(AddConsoleEvent.OnModelChange(it)) },
        onPriceChange = { viewModel.handleEvent(AddConsoleEvent.OnPriceChange(it)) },
        onAddConsole = {
            viewModel.handleEvent(AddConsoleEvent.AddConsole(state.userId, it))
        }
    )
}

@Composable
fun AddConsoleScreenContent(
    state: AddConsoleState,
    onNameChange: (String) -> Unit = {},
    onModelChange: (String) -> Unit = {},
    onPriceChange: (String) -> Unit = {},
    onAddConsole: (Console) -> Unit = {}
) {
    Surface {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (state.isLoading) {
                CircularProgressIndicator()
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(id = R.dimen.padding_default)),
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.spacing_default))
                ) {
                    Text(stringResource(R.string.add_new_console), fontSize = dimensionResource(id = R.dimen.text_size_title).value.sp)
                    Text(
                        stringResource(R.string.user_id, state.userId),
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        fontSize = dimensionResource(id = R.dimen.text_size_subtitle).value.sp
                    )
                    OutlinedTextField(
                        value = state.console.nombre,
                        onValueChange = onNameChange,
                        label = { Text(stringResource(R.string.name)) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = state.console.modelo,
                        onValueChange = onModelChange,
                        label = { Text(stringResource(R.string.model)) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = state.console.precio.toString(),
                        onValueChange = onPriceChange,
                        label = { Text(stringResource(R.string.price)) },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Button(
                        onClick = { onAddConsole(state.console) },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text(stringResource(R.string.add_console))
                    }
                }
            }
        }
    }
}

class AddConsoleStateProvider : PreviewParameterProvider<AddConsoleState> {
    override val values = sequenceOf(
        AddConsoleState(userId = 1, isLoading = false),
        AddConsoleState(
            userId = 2,
            console = Console(1, "Console 1", "Model 1", 100.0),
            isLoading = false
        ),
        AddConsoleState(
            userId = 3,
            console = Console(1, "Console 2", "Model 2", 100.0),
            isLoading = true
        )
    )
}

@Preview(showBackground = true, device = Devices.PHONE)
@Composable
fun AddConsoleScreenPreview(@PreviewParameter(AddConsoleStateProvider::class) state: AddConsoleState) {
    AddConsoleScreenContent(state = state)
}