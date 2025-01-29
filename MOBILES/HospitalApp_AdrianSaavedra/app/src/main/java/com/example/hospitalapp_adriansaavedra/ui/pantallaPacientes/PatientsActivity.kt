package com.example.hospitalapp_adriansaavedra.ui.pantallaPacientes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.hospitalapp_adriansaavedra.R
import com.example.hospitalapp_adriansaavedra.domain.modelo.Patient
import com.example.hospitalapp_adriansaavedra.ui.common.UiEvent

@Composable
fun PatientListScreen(
    showSnackbar: (String) -> Unit,
    onNavigateToDetail: (Int) -> Unit,
    viewModel: PatientsViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.handleEvent(PatientsEvent.GetPatients)
    }

    LaunchedEffect(state.aviso) {
        state.aviso?.let {
            when (it) {
                is UiEvent.ShowSnackbar -> {
                    showSnackbar(it.message)
                    viewModel.handleEvent(PatientsEvent.AvisoVisto)
                }

                is UiEvent.Navigate -> {
                    onNavigateToDetail(state.selectedPatientId)
                    viewModel.handleEvent(PatientsEvent.AvisoVisto)
                }
            }
        }
    }

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                LazyColumn {
                    items(state.patients) { patient ->
                        PatientCard(
                            patient = patient,
                            onCardClick = { viewModel.handleEvent(PatientsEvent.OnPlayClick(patient.id)) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PatientCard(patient: Patient, onCardClick: () -> Unit) {
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
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = stringResource(R.string.patient_id) + "${patient.id}",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(R.string.patient_name) + patient.name,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            IconButton(onClick = onCardClick) {
                Icon(Icons.Filled.PlayArrow, contentDescription = null)
            }
        }
    }
}
