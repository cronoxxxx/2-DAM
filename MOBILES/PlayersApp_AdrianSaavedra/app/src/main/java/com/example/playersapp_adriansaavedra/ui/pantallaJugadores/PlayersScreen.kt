package com.example.playersapp_adriansaavedra.ui.pantallaJugadores

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.playersapp_adriansaavedra.domain.model.Player
import com.example.playersapp_adriansaavedra.ui.common.UiEvent

@Composable
fun PlayersScreen(
    showSnackbar: (String) -> Unit,
    viewModel: PlayersViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()


    LaunchedEffect(Unit) {
        viewModel.handleEvent(PlayersEvent.OnGetPlayers)
    }

    LaunchedEffect(state.aviso) {
        state.aviso?.let {
            if (it is UiEvent.ShowSnackbar) {
                showSnackbar(it.message)
                viewModel.handleEvent(PlayersEvent.OnAvisoVisto)
            }
        }
    }

    PlayersScreenContent(state = state)
}

@Composable
fun PlayersScreenContent(
    state: PlayersState
) {
    Surface {
        Box(modifier = Modifier.fillMaxSize()) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                state.players.isEmpty() -> {
                    Text(
                        "No players found",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                else -> {
                    LazyColumn {
                        items(state.players) { player ->
                            PlayerCard(player = player)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PlayerCard(player: Player) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = player.name,
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "ID: ${player.id}",
                    style = MaterialTheme.typography.bodyMedium
                )

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlayersScreenPreview() {
    MaterialTheme {
        PlayersScreenContent(
            state = PlayersState(
                players = listOf(
                    Player(1, "Lionel Messi", "Inter Miami", "Argentina"),
                    Player(2, "Cristiano Ronaldo", "Al-Nassr", "Portugal"),
                    Player(3, "Kylian Mbappe", "Real Madrid", "France")
                )
            )
        )
    }
}