package com.example.consolesapp_adriansaavedra.ui.pantallaConsolaDetalle

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.consolesapp_adriansaavedra.domain.model.Console
import com.example.consolesapp_adriansaavedra.ui.common.UiEvent

@Composable
fun ConsoleDetailScreen(
    showSnackbar: (String) -> Unit,
    consoleId: Int,
    onNavigateBack: () -> Unit,
    viewModel: ConsoleDetailViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(consoleId) {
        viewModel.handleEvent(ConsoleDetailEvent.GetConsole(consoleId))
    }

    LaunchedEffect(state.aviso) {
        state.aviso?.let {
            when (it) {
                is UiEvent.ShowSnackbar -> {
                    showSnackbar(it.message)
                    viewModel.handleEvent(ConsoleDetailEvent.AvisoVisto)
                }

                is UiEvent.Navigate -> {
                    onNavigateBack()
                    viewModel.handleEvent(ConsoleDetailEvent.AvisoVisto)
                }
            }
        }
    }

    ConsoleDetailScreenContent(
        state = state,
        onNameChange = { viewModel.handleEvent(ConsoleDetailEvent.OnNameChange(it)) },
        onModelChange = { viewModel.handleEvent(ConsoleDetailEvent.OnModelChange(it)) },
        onPriceChange = { viewModel.handleEvent(ConsoleDetailEvent.OnPriceChange(it)) },
        onUpdateClick = {
            state.console?.let { console ->
                viewModel.handleEvent(
                    ConsoleDetailEvent.UpdateConsole(
                        console
                    )
                )
            }
        },
        onDeleteClick = {
            state.console?.let { console ->
                viewModel.handleEvent(ConsoleDetailEvent.DeleteConsole(console.consolaId))
            }
        }
    )
}

@Composable
fun ConsoleDetailScreenContent(
    state: ConsoleDetailState,
    onNameChange: (String) -> Unit = {},
    onModelChange: (String) -> Unit = {},
    onPriceChange: (String) -> Unit = {},
    onUpdateClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {}
) {
    Surface {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (state.isLoading) {
                CircularProgressIndicator()
            } else {
                state.console?.let { console ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text("Console Detail", fontSize = 24.sp)
                        OutlinedTextField(
                            value = console.nombre,
                            onValueChange = onNameChange,
                            label = { Text("Name") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = console.modelo,
                            onValueChange = onModelChange,
                            label = { Text("Model") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = console.precio.toString(),
                            onValueChange = onPriceChange,
                            label = { Text("Price") },
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                            modifier = Modifier.fillMaxWidth()
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Button(
                                onClick = onUpdateClick,
                                modifier = Modifier.padding(end = 8.dp)
                            ) {
                                Text("Update")
                            }
                            Button(
                                onClick = onDeleteClick,
                                modifier = Modifier.padding(start = 8.dp)
                            ) {
                                Text("Delete")
                            }
                        }
                    }
                } ?: Text("No console data available")
            }
        }
    }
}

class ConsoleDetailStateProvider : PreviewParameterProvider<ConsoleDetailState> {
    override val values = sequenceOf(
        ConsoleDetailState(isLoading = true),
        ConsoleDetailState(console = Console(1, "PlayStation 5", "PS5", 499.99)),
        ConsoleDetailState(console = Console(2, "Xbox Series X", "XBSX", 499.99))
    )
}

@Preview(showBackground = true, device = Devices.PHONE)
@Composable
fun ConsoleDetailScreenPreview(@PreviewParameter(ConsoleDetailStateProvider::class) state: ConsoleDetailState) {
    ConsoleDetailScreenContent(state = state)
}