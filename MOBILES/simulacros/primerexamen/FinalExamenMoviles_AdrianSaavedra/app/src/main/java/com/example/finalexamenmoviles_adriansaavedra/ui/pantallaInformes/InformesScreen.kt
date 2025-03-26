package com.example.finalexamenmoviles_adriansaavedra.ui.pantallaInformes

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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.finalexamenmoviles_adriansaavedra.domain.model.Informe
import com.example.finalexamenmoviles_adriansaavedra.ui.common.UiEvent

@Composable
fun InformesScreen(
    showSnackbar: (String) -> Unit,
    onNavigateToDetail: (Int) -> Unit,
    viewModel: InformesViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    
    LaunchedEffect(Unit) {
        viewModel.handleEvent(InformesEvent.GetInformes)
    }
    
    LaunchedEffect(state.aviso) {
        state.aviso?.let {
            when (it) {
                is UiEvent.ShowSnackbar -> {
                    showSnackbar(it.message)
                    viewModel.handleEvent(InformesEvent.AvisoVisto)
                }
                is UiEvent.Navigate -> {
                    onNavigateToDetail(state.selectedInformeId)
                    viewModel.handleEvent(InformesEvent.AvisoVisto)
                }
            }
        }
    }
    
    InformesScreenContent(
        state = state,
        onInformeClick = { informeId ->
            viewModel.handleEvent(InformesEvent.InformeSelected(informeId))
        },
        onLoadInitialInformes = {
            viewModel.handleEvent(InformesEvent.LoadInitialInformes)
        }
    )
}

@Composable
fun InformesScreenContent(
    state: InformesState,
    onInformeClick: (Int) -> Unit = {},
    onLoadInitialInformes: () -> Unit = {}
) {
    Surface {
        Box(modifier = Modifier.fillMaxSize()) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                state.informes.isEmpty() -> {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("No hay informes disponibles")
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = onLoadInitialInformes) {
                            Text("Cargar informes iniciales")
                        }
                    }
                }
                else -> {
                    LazyColumn {
                        items(state.informes) { informe ->
                            InformeCard(
                                informe = informe,
                                onClick = { onInformeClick(informe.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun InformeCard(informe: Informe, onClick: () -> Unit) {
    val backgroundColor = when (informe.nivel) {
        1 -> Color.Blue
        2 -> Color(0xFF9C27B0)
        3 -> Color.Red
        else -> Color.Gray
    }
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = informe.nombre,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Nivel: ${informe.nivel}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

