package com.example.consolesapp_adriansaavedra.ui.pantallaJugadores

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
import com.example.consolesapp_adriansaavedra.domain.model.Player
import com.example.consolesapp_adriansaavedra.ui.common.UiEvent

@Composable
fun PlayersScreen(
    showSnackbar: (String) -> Unit,
    onNavigateToDetail: (Int) -> Unit,
    viewModel: PlayersViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.handleEvent(PlayersEvent.GetPlayers)
    }

    LaunchedEffect(state.aviso) {
        state.aviso?.let {
            when (it) {
                is UiEvent.ShowSnackbar -> {
                    showSnackbar(it.message)
                    viewModel.handleEvent(PlayersEvent.AvisoVisto)
                }

                is UiEvent.Navigate -> {
                    onNavigateToDetail(state.selectedPlayerId)
                    viewModel.handleEvent(PlayersEvent.AvisoVisto)
                }
            }
        }
    }

    PlayersScreenContent(
        state = state,
        onPlayerClick = {
            viewModel.handleEvent(PlayersEvent.SelectConsole(it))
        }
    )
}

@Composable
fun PlayerCard(player: Player, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.padding_small))
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(id = R.dimen.spacing_small))
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
                    text = stringResource(R.string.player_id, player.jugadorId),
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_small)))
                Text(
                    text = stringResource(R.string.username, player.username),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
fun PlayersScreenContent(
    state: PlayersState,
    onPlayerClick: (Int) -> Unit = {}
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
                                onClick = { onPlayerClick(player.jugadorId) }
                            )
                        }
                    }
                }
            }
        }
    }
}

class PlayersStateProvider : PreviewParameterProvider<PlayersState> {
    override val values = sequenceOf(
        PlayersState(isLoading = true),
        PlayersState(players = emptyList()),
        PlayersState(
            players = listOf(
                Player(jugadorId = 1, username = "Player1"),
                Player(jugadorId = 2, username = "Player2"),
                Player(jugadorId = 3, username = "Player3")
            )
        )
    )
}

@Preview(showSystemUi = true, showBackground = true, device = Devices.PHONE)
@Composable
fun PlayersScreenPreview(@PreviewParameter(PlayersStateProvider::class) state: PlayersState) {
    PlayersScreenContent(state = state)
}