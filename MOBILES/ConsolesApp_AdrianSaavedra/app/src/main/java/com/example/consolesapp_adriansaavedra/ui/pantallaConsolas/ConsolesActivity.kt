package com.example.consolesapp_adriansaavedra.ui.pantallaConsolas

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import com.example.consolesapp_adriansaavedra.R
import com.example.consolesapp_adriansaavedra.domain.model.Console
import com.example.consolesapp_adriansaavedra.ui.common.UiEvent

@Composable
fun ConsolesScreen(
    showSnackbar: (String) -> Unit,
    onNavigateToDetail: (Int) -> Unit,
    viewModel: ConsolesViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val userId by viewModel.userId.collectAsStateWithLifecycle()

    LaunchedEffect(userId) {
        if (userId > 0) {
            viewModel.handleEvent(ConsolesEvent.LoadConsoles(userId))
        }
    }

    LaunchedEffect(state.aviso) {
        state.aviso?.let {
            when (it) {
                is UiEvent.ShowSnackbar -> {
                    showSnackbar(it.message)
                    viewModel.handleEvent(ConsolesEvent.AvisoVisto)
                }

                is UiEvent.Navigate -> {
                    onNavigateToDetail(state.selectedConsoleId)
                    viewModel.handleEvent(ConsolesEvent.AvisoVisto)
                }
            }
        }
    }

    ConsolesScreenContent(
        state = state,
        onConsolesClick = { consoleId ->
            viewModel.handleEvent(ConsolesEvent.OnConsolesClick(consoleId))
        }
    )
}

@Composable
fun ConsoleCard(console: Console, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.padding_small))
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(id = R.dimen.card_elevation))
    ) {
        Row(
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.padding_medium))
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = stringResource(R.string.console_id, console.consolaId),
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_small)))
                Text(
                    text = stringResource(R.string.name_args, console.nombre),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
fun ConsolesScreenContent(
    state: ConsolesState,
    onConsolesClick: (Int) -> Unit = {}
) {
    Surface {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                state.playerConsoles.isEmpty() -> {
                    Text(
                        stringResource(R.string.no_consoles_found),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                else -> {
                    LazyColumn {
                        items(state.playerConsoles) { console ->
                            ConsoleCard(
                                console = console,
                                onClick = { onConsolesClick(console.consolaId) }
                            )
                        }
                    }
                }
            }
        }
    }
}

class ConsolesStateProvider : PreviewParameterProvider<ConsolesState> {
    override val values = sequenceOf(
        ConsolesState(isLoading = true),
        ConsolesState(playerConsoles = emptyList()),
        ConsolesState(
            playerConsoles = listOf(
                Console(consolaId = 1, nombre = "PlayStation 5"),
                Console(consolaId = 2, nombre = "Xbox Series X"),
                Console(consolaId = 3, nombre = "Nintendo Switch")
            )
        )
    )
}

@Preview(showSystemUi = true, showBackground = true, device = Devices.PHONE)
@Composable
fun ConsoleScreenPreview(@PreviewParameter(ConsolesStateProvider::class) state: ConsolesState) {
    ConsolesScreenContent(state = state)
}



