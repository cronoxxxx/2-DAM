package com.example.playersapp_adriansaavedra.ui.pantallaAgregarFavoritos

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.playersapp_adriansaavedra.R
import com.example.playersapp_adriansaavedra.ui.common.UiEvent

@Composable
fun AddFavoritesScreen(
    showSnackbar: (String) -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: AddFavoritesViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val userId by viewModel.userId.collectAsStateWithLifecycle()

    LaunchedEffect(userId) {
        viewModel.handleEvent(AddFavoritesEvent.UpdateUserId(userId))
    }

    LaunchedEffect(state.aviso) {
        state.aviso?.let {
            when (it) {
                is UiEvent.ShowSnackbar -> {
                    showSnackbar(it.message)
                    viewModel.handleEvent(AddFavoritesEvent.AvisoVisto)
                }
                is UiEvent.Navigate -> {
                    onNavigateBack()
                    viewModel.handleEvent(AddFavoritesEvent.AvisoVisto)
                }
            }
        }
    }

    AddFavoritesScreenContent(
        state = state,
        onNameChange = { viewModel.handleEvent(AddFavoritesEvent.OnNameChange(it)) },
        onAddFavorite = {
            viewModel.handleEvent(AddFavoritesEvent.AddFavoritePlayer(state.userId, state.playerName))
        }
    )
}

@Composable
fun AddFavoritesScreenContent(
    state: AddFavoritesState,
    onNameChange: (String) -> Unit = {},
    onAddFavorite: () -> Unit = {}
) {
    Surface {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (state.isLoading) {
                CircularProgressIndicator()
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(id = R.dimen.padding_default)),
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.spacing_default))
                ) {
                    Text(
                        stringResource(R.string.add_new_favorite),
                        fontSize = dimensionResource(id = R.dimen.text_size_title).value.sp
                    )
                    Text(
                        stringResource(R.string.user_id, state.userId),
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        fontSize = dimensionResource(id = R.dimen.text_size_subtitle).value.sp
                    )
                    OutlinedTextField(
                        value = state.playerName,
                        onValueChange = onNameChange,
                        label = { Text(stringResource(R.string.player_name)) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Button(
                        onClick = onAddFavorite,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text(stringResource(R.string.add_favorite))
                    }
                }
            }
        }
    }
}

class AddFavoritesStateProvider : PreviewParameterProvider<AddFavoritesState> {
    override val values = sequenceOf(
        AddFavoritesState(userId = 1, isLoading = false),
        AddFavoritesState(userId = 2, playerName = "Player 1", isLoading = false),
        AddFavoritesState(userId = 3, playerName = "Player 2", isLoading = true)
    )
}

@Preview(showBackground = true, device = Devices.PHONE)
@Composable
fun AddFavoritesScreenPreview(@PreviewParameter(AddFavoritesStateProvider::class) state: AddFavoritesState) {
    AddFavoritesScreenContent(state = state)
}