package com.example.playersapp_adriansaavedra.ui.pantallaJugador

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.sp
import com.example.playersapp_adriansaavedra.R
import com.example.playersapp_adriansaavedra.domain.model.Player
import com.example.playersapp_adriansaavedra.ui.common.UiEvent

@Composable
fun PlayerDetailScreen(
    showSnackbar: (String) -> Unit,
    playerId: Int,
    viewModel: PlayerDetailViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(playerId) {
        viewModel.handleEvent(PlayerDetailEvent.GetPlayer(playerId))
    }

    LaunchedEffect(state.aviso) {
        state.aviso?.let {
            if (it is UiEvent.ShowSnackbar) {
                showSnackbar(it.message)
                viewModel.handleEvent(PlayerDetailEvent.AvisoVisto)
            }
        }
    }

    PlayerDetailScreenContent(state = state)
}

@Composable
fun PlayerDetailScreenContent(
    state: PlayerDetailState
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
                            stringResource(R.string.player_detail),
                            fontSize = dimensionResource(id = R.dimen.text_size_title).value.sp,
                            fontWeight = FontWeight.Bold
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

                    }
                } ?: Text(stringResource(R.string.no_player_data))
            }
        }
    }
}

class PlayerDetailStateProvider : PreviewParameterProvider<PlayerDetailState> {
    override val values = sequenceOf(
        PlayerDetailState(isLoading = true, player = null),
        PlayerDetailState(player = Player(1, "Lionel Messi", "Inter Miami", "Argentina"), isLoading = false),
    )
}

@Preview(showBackground = true, device = Devices.PHONE)
@Composable
fun PlayerDetailScreenPreview(@PreviewParameter(PlayerDetailStateProvider::class) state: PlayerDetailState) {
    PlayerDetailScreenContent(state = state)
}