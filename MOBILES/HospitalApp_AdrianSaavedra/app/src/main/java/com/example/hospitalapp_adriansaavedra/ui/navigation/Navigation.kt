package com.example.hospitalapp_adriansaavedra.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.hospitalapp_adriansaavedra.R
import com.example.hospitalapp_adriansaavedra.ui.common.BottomBar
import com.example.hospitalapp_adriansaavedra.ui.common.TopBar
import com.example.hospitalapp_adriansaavedra.ui.pantallaLogin.LoginScreen
import com.example.hospitalapp_adriansaavedra.ui.pantallaMedRecord.MedRecordDetailScreen
import com.example.hospitalapp_adriansaavedra.ui.pantallaMedRecords.MedRecordsScreen
import com.example.hospitalapp_adriansaavedra.ui.pantallaPaciente.PatientDetailScreen
import com.example.hospitalapp_adriansaavedra.ui.pantallaPacientes.PatientListScreen
import kotlinx.coroutines.launch

class PatientSession {
    companion object {
        var id: Int = -1
    }
}


@Composable
fun Navigation() {
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    var topBarTitle by remember { mutableStateOf("") }
    var showBackButton by remember { mutableStateOf(false) }

    val showSnackbar: (String) -> Unit = { message ->
        coroutineScope.launch {
            snackbarHostState.showSnackbar(message, duration = SnackbarDuration.Short)
        }
    }

    Scaffold(
        topBar = {
            TopBar(
                title = topBarTitle,
                showBackButton = showBackButton,
                onBackClick = { navController.navigateUp() })
        },
        bottomBar = {
            BottomBar(navController = navController, items = rememberBottomNavItems())
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = LoginDestination,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<LoginDestination> {
                topBarTitle = stringResource(R.string.login)
                showBackButton = false
                LoginScreen(
                    navigateToMedRecords = { patientId ->
                        PatientSession.id = patientId
                        navController.navigate(MedRecordListDestination)
                    },
                    showSnackbar = showSnackbar,
                )
            }

            composable<MedRecordListDestination> {
                topBarTitle = stringResource(R.string.med_records_patient) + "${PatientSession.id}"
                showBackButton = false
                MedRecordsScreen(
                    patientId = PatientSession.id,
                    onNavigateToDetail = { _, recordId ->
                        navController.navigate(
                            MedRecordDetailDestination(
                                PatientSession.id,
                                recordId
                            )
                        )
                    },
                    showSnackbar = showSnackbar
                )
            }

            composable<MedRecordDetailDestination> { backStackEntry ->
                val destination = backStackEntry.toRoute() as MedRecordDetailDestination
                topBarTitle = stringResource(R.string.record_detail)
                showBackButton = true
                MedRecordDetailScreen(
                    patientId = destination.patientId,
                    recordId = destination.recordId,
                    showSnackbar = showSnackbar
                )
            }

            composable<PatientListDestination> {
                topBarTitle = stringResource(R.string.patient_list)
                showBackButton = false
                PatientListScreen(
                    onNavigateToDetail = { patientId ->
                        navController.navigate(PatientDetailDestination(patientId))
                    },
                    showSnackbar = showSnackbar
                )
            }

            composable<PatientDetailDestination> { backStackEntry ->
                val destination = backStackEntry.toRoute() as PatientDetailDestination
                topBarTitle = stringResource(R.string.patient_detail)
                showBackButton = true
                PatientDetailScreen(
                    patientId = destination.patientId,
                    showSnackbar = showSnackbar
                )
            }
        }
    }
}




