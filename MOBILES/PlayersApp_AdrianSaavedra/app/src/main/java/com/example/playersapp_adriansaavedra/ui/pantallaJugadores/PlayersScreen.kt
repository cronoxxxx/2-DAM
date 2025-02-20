package com.example.playersapp_adriansaavedra.ui.pantallaJugadores

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.playersapp_adriansaavedra.R
import com.example.playersapp_adriansaavedra.domain.model.Player
import com.example.playersapp_adriansaavedra.ui.common.UiEvent

@Composable
fun PlayersScreen(
    showSnackbar: (String) -> Unit,
    onNavigateToDetail: (Int) -> Unit,
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
            } else if (it is UiEvent.Navigate) {
                onNavigateToDetail(state.selectedPlayerId)
                viewModel.handleEvent(PlayersEvent.OnAvisoVisto)
            }
        }
    }

    PlayersScreenContent(
        state = state,
        onConsolesClick = { playerId ->
            viewModel.handleEvent(PlayersEvent.OnPlayerSelected(playerId))
        }
    )
}

@Composable
fun PlayersScreenContent(
    state: PlayersState,
    onConsolesClick: (Int) -> Unit = {}
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
                        stringResource(R.string.no_players_found),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                else -> {
                    LazyColumn {
                        items(state.players) { player ->
                            PlayerCard(
                                player = player,
                                onClick = { onConsolesClick(player.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PlayerCard(player: Player, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.card_padding))
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(R.dimen.card_elevation))
    ) {
        Row(
            modifier = Modifier
                .padding(dimensionResource(R.dimen.card_content_padding))
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = player.name,
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.text_spacing)))
                Text(
                    text = stringResource(R.string.player_id, player.id),
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