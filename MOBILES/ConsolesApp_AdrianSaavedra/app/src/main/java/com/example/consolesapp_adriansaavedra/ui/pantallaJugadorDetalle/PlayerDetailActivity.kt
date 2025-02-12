package com.example.consolesapp_adriansaavedra.ui.pantallaJugadorDetalle

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.consolesapp_adriansaavedra.R
import com.example.consolesapp_adriansaavedra.domain.model.Console
import com.example.consolesapp_adriansaavedra.domain.model.Player
import com.example.consolesapp_adriansaavedra.ui.common.UiEvent

@Composable
fun PlayerDetailScreen(
    showSnackbar: (String) -> Unit,
    playerId: Int,
    onNavigateBack: () -> Unit,
    viewModel: PlayerDetailViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(playerId) {
        viewModel.handleEvent(PlayerDetailEvent.GetPlayerConsoles(playerId))
    }

    LaunchedEffect(state.aviso) {
        state.aviso?.let {
            when (it) {
                is UiEvent.ShowSnackbar -> {
                    showSnackbar(it.message)
                    viewModel.handleEvent(PlayerDetailEvent.AvisoVisto)
                }

                is UiEvent.Navigate -> {
                    onNavigateBack()
                    viewModel.handleEvent(PlayerDetailEvent.AvisoVisto)
                }
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
                            stringResource(R.string.username, player.username),
                            fontSize = dimensionResource(id = R.dimen.text_size_body_large).value.sp
                        )
                        Text(
                            stringResource(R.string.password, player.password),
                            fontSize = dimensionResource(id = R.dimen.text_size_body_large).value.sp
                        )
                        Text(
                            stringResource(R.string.consoles),
                            fontSize = dimensionResource(id = R.dimen.text_size_subtitle).value.sp,
                            fontWeight = FontWeight.Bold
                        )
                        if (player.consolasList.isEmpty()) {
                            Text(
                                stringResource(R.string.no_consoles),
                                fontSize = dimensionResource(id = R.dimen.text_size_body).value.sp,
                                fontStyle = FontStyle.Italic,
                                color = MaterialTheme.colorScheme.error
                            )
                        } else {
                            LazyColumn {
                                items(player.consolasList) { console ->
                                    Text(
                                        stringResource(R.string.console_item, console.nombre, console.modelo),
                                        fontSize = dimensionResource(id = R.dimen.text_size_body).value.sp
                                    )
                                }
                            }
                        }
                    }
                } ?: Text(stringResource(R.string.no_player_data))
            }
        }
    }
}


class PlayerDetailStateProvider : PreviewParameterProvider<PlayerDetailState> {
    override val values = sequenceOf(
        PlayerDetailState(isLoading = true),
        PlayerDetailState(player = Player(1, "JohnDoe", "password123", emptyList())),
        PlayerDetailState(
            player = Player(
                2, "JaneSmith", "securePass", listOf(
                    Console(1, "PlayStation 5", "PS5", 499.99),
                    Console(2, "Xbox Series X", "XBSX", 499.99),
                    Console(3, "Nintendo Switch", "OLED", 349.99)
                )
            )
        )
    )
}

@Preview(showBackground = true, device = Devices.PHONE)
@Composable
fun PlayerDetailScreenPreview(@PreviewParameter(PlayerDetailStateProvider::class) state: PlayerDetailState) {
    PlayerDetailScreenContent(state = state)
}