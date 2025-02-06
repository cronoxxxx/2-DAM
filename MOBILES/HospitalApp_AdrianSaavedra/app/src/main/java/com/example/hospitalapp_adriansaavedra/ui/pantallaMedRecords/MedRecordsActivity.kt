package com.example.hospitalapp_adriansaavedra.ui.pantallaMedRecords

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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.hospitalapp_adriansaavedra.R
import com.example.hospitalapp_adriansaavedra.domain.modelo.MedRecord
import com.example.hospitalapp_adriansaavedra.ui.common.UiEvent

@Composable
fun MedRecordsScreen(
    patientId: Int,
    showSnackbar: (String) -> Unit,
    onNavigateToDetail: (Int, Int) -> Unit,
    viewModel: MedRecordsViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(patientId) {
        viewModel.handleEvent(MedRecordsEvent.GetPatientRecords(patientId))
    }

    LaunchedEffect(state.aviso) {
        state.aviso?.let {
            when (it) {
                is UiEvent.ShowSnackbar -> {
                    showSnackbar(it.message)
                    viewModel.handleEvent(MedRecordsEvent.AvisoVisto)
                }

                is UiEvent.Navigate -> {
                    onNavigateToDetail(patientId, state.selectedRecordId)
                    viewModel.handleEvent(MedRecordsEvent.AvisoVisto)
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
                    items(state.patientsRecords) { record ->
                        MedRecordCard(
                            record = record,
                            onPlayClick = { viewModel.handleEvent(MedRecordsEvent.OnPlayClick(record.id)) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MedRecordCard(record: MedRecord, onPlayClick: () -> Unit) {
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
                    text = stringResource(R.string.record_id) + "${record.id}",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(R.string.date) + record.date,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(R.string.patient_id) + "${record.idPatient}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            IconButton(onClick = onPlayClick) {
                Icon(Icons.Filled.PlayArrow, contentDescription = null)
            }
        }
    }
}

