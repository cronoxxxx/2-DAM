package com.example.playersapp_adriansaavedra.ui.pantallaFavoritos

import androidx.compose.foundation.clickable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.playersapp_adriansaavedra.R
import com.example.playersapp_adriansaavedra.domain.model.Player
import com.example.playersapp_adriansaavedra.ui.common.UiEvent

@Composable
fun FavoritesScreen(
    showSnackbar: (String) -> Unit,
    onNavigateToDetail: (Int) -> Unit,
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val userId by viewModel.userId.collectAsStateWithLifecycle()

    LaunchedEffect(userId) {
        if (userId > 0) {
            viewModel.handleEvent(FavoritesEvent.OnGetFavoritePlayers(userId))
        }
    }

    LaunchedEffect(state.aviso) {
        state.aviso?.let {
            when (it) {
                is UiEvent.ShowSnackbar -> {
                    showSnackbar(it.message)
                    viewModel.handleEvent(FavoritesEvent.OnAvisoVisto)
                }
                is UiEvent.Navigate -> {
                    onNavigateToDetail(state.selectedPlayerId)
                    viewModel.handleEvent(FavoritesEvent.OnAvisoVisto)
                }
            }
        }
    }

    FavoritesScreenContent(
        state = state,
        onPlayerClick = { playerId ->
            viewModel.handleEvent(FavoritesEvent.OnPlayerClick(playerId))
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
                    text = stringResource(R.string.player_id, player.id),
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_small)))
                Text(
                    text = stringResource(R.string.name_args, player.name),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
fun FavoritesScreenContent(
    state: FavoritesState,
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
                state.favoritePlayers.isEmpty() -> {
                    Text(
                        stringResource(R.string.no_favorite_players_found),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                else -> {
                    LazyColumn {
                        items(state.favoritePlayers) { player ->
                            PlayerCard(
                                player = player,
                                onClick = { onPlayerClick(player.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}