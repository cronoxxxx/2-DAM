package com.example.playersapp_adriansaavedra.ui.pantallaFavorito

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.playersapp_adriansaavedra.R
import com.example.playersapp_adriansaavedra.domain.model.Player
import com.example.playersapp_adriansaavedra.ui.common.UiEvent

@Composable
fun FavoriteDetailScreen(
    showSnackbar: (String) -> Unit,
    playerId: Int,
    onNavigateBack: () -> Unit,
    viewModel: FavoriteDetailViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val userId by viewModel.userId.collectAsStateWithLifecycle()

    LaunchedEffect(userId) {
        userId?.takeIf { it > 0 }?.let { id ->
            viewModel.handleEvent(FavoriteDetailEvent.UpdateUserId(id))
            viewModel.handleEvent(FavoriteDetailEvent.GetFavPlayer(id, playerId))
        }
    }

    LaunchedEffect(state.aviso) {
        state.aviso?.let {
            when (it) {
                is UiEvent.ShowSnackbar -> {
                    showSnackbar(it.message)
                    viewModel.handleEvent(FavoriteDetailEvent.AvisoVisto)
                }

                is UiEvent.Navigate -> {
                    onNavigateBack()
                    viewModel.handleEvent(FavoriteDetailEvent.AvisoVisto)
                }
            }
        }
    }

    FavoriteDetailScreenContent(
        state = state,
        onDeleteClick = {
            viewModel.handleEvent(FavoriteDetailEvent.DeleteFavPlayer(state.userId, playerId))
        }
    )
}


@Composable
fun FavoriteDetailScreenContent(
    state: FavoriteDetailState,
    onDeleteClick: () -> Unit
) {
    Surface {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (state.isLoading) {
                CircularProgressIndicator()
            } else {
                state.player?.let { player ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(dimensionResource(id = R.dimen.padding_medium)),
                        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
                    ) {
                        Text(
                            stringResource(R.string.favorite_player_detail),
                            fontSize = dimensionResource(id = R.dimen.text_size_title).value.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            stringResource(R.string.user_id, state.userId),
                            fontSize = dimensionResource(id = R.dimen.text_size_body_large).value.sp
                        )
                        Text(
                            stringResource(R.string.player_id, player.id),
                            fontSize = dimensionResource(id = R.dimen.text_size_body_large).value.sp
                        )
                        Text(
                            stringResource(R.string.name_args, player.name),
                            fontSize = dimensionResource(id = R.dimen.text_size_body_large).value.sp
                        )
                        Text(
                            stringResource(R.string.team_args, player.team),
                            fontSize = dimensionResource(id = R.dimen.text_size_body_large).value.sp
                        )
                        Text(
                            stringResource(R.string.country_args, player.country),
                            fontSize = dimensionResource(id = R.dimen.text_size_body_large).value.sp
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Button(
                            onClick = onDeleteClick,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(stringResource(R.string.delete_favorite_player))
                        }
                    }
                } ?: Text(stringResource(R.string.no_favorite_player_data))
            }
        }
    }
}

class FavoriteDetailStateProvider : PreviewParameterProvider<FavoriteDetailState> {
    override val values = sequenceOf(
        FavoriteDetailState(isLoading = true, player = null),
        FavoriteDetailState(
            player = Player(1, "Lionel Messi", "Inter Miami", "Argentina"),
            isLoading = false
        ),
    )
}

@Preview(showBackground = true, device = Devices.PHONE)
@Composable
fun FavoriteDetailScreenPreview(@PreviewParameter(FavoriteDetailStateProvider::class) state: FavoriteDetailState) {
    FavoriteDetailScreenContent(state = state, onDeleteClick = {})
}