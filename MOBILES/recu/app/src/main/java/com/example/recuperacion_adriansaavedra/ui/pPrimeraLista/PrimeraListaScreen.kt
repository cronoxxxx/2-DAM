package com.example.recuperacion_adriansaavedra.ui.pPrimeraLista
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.*
import androidx.lifecycle.compose.*
import com.example.finalexamenmoviles_adriansaavedra.ui.common.UiEvent

@Composable
fun PrimeraListaScreen(
    showSnackbar: (String) -> Unit,
    onNavigateToDetail: (String) -> Unit = {}, // Función para navegar al detalle si es necesario
    viewModel: PrimeraListaViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    // Cargar datos al entrar en la pantalla
    LaunchedEffect(Unit) {
        viewModel.handleEvent(PrimeraListaEvent.CargarDatos)
    }

    // Observar eventos de navegación o mensajes
    LaunchedEffect(state.aviso) {
        state.aviso?.let {
            when (it) {
                is UiEvent.ShowSnackbar -> {
                    showSnackbar(it.message)
                    viewModel.handleEvent(PrimeraListaEvent.AvisoVisto)
                }
                is UiEvent.Navigate -> {
                    // Si necesitas navegar a algún lado
                    viewModel.handleEvent(PrimeraListaEvent.AvisoVisto)
                }
            }
        }
    }

    PrimeraListaContent(
        state = state,
        onItemClick = { item ->
            viewModel.handleEvent(PrimeraListaEvent.ItemSeleccionado(item))
        }
    )
}

@Composable
fun PrimeraListaContent(
    state: PrimeraListaState,
    onItemClick: (String) -> Unit = {}
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize()) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                state.items.isEmpty() -> {
                    Text(
                        text = "No hay elementos disponibles en pantalla1",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                else -> {
                    // DURANTE EL EXAMEN: Reemplazar con tu implementación de lista
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        items(state.items) { item ->
                            ItemCard(
                                item = item,
                                onClick = { onItemClick(item) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ItemCard(
    item: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = item,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

