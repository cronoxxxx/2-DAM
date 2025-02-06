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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Label
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.consolesapp_adriansaavedra.domain.model.Console
import com.example.consolesapp_adriansaavedra.ui.common.PreferencesViewModel
import com.example.consolesapp_adriansaavedra.ui.common.UiEvent

@Composable
fun ConsoleScreen(
    showSnackbar: (String) -> Unit,
    onNavigateToDetail: (Int) -> Unit,
    viewModel: ConsoleViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(state.aviso) {
        state.aviso?.let {
            when (it) {
                is UiEvent.ShowSnackbar -> {
                    showSnackbar(it.message)
                    viewModel.handleEvent(ConsoleEvent.AvisoVisto)
                }
                is UiEvent.Navigate -> {
                    onNavigateToDetail(state.selectedConsoleId)
                    viewModel.handleEvent(ConsoleEvent.AvisoVisto)
                }
            }
        }
    }

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
                        "No consoles found",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                else -> {
                    LazyColumn {
                        items(state.playerConsoles) { console ->
                            ConsoleCard(
                                console = console,
                                onClick = { viewModel.handleEvent(ConsoleEvent.OnConsoleClick(console.consolaId)) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ConsoleCard(console: Console, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Console ID: ${console.consolaId}",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Name: ${console.nombre}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}


