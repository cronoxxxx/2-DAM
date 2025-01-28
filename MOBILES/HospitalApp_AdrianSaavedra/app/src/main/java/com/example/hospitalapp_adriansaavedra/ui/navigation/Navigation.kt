package com.example.hospitalapp_adriansaavedra.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.hospitalapp_adriansaavedra.ui.common.BottomBar
import com.example.hospitalapp_adriansaavedra.ui.common.BottomNavItem
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

    val showSnackbar: (String) -> Unit = { message ->
        coroutineScope.launch {
            snackbarHostState.showSnackbar(message, duration = SnackbarDuration.Short)
        }
    }

    Scaffold(
        bottomBar = {
            BottomBar(navController = navController, items = bottomNavItems)
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
                LoginScreen(
                    navigateToMedRecords = { patientId ->
                        PatientSession.id = patientId
                        navController.navigate(MedRecordDestination)
                    },
                    showSnackbar = showSnackbar
                )
            }

            composable<MedRecordDestination> {
                MedRecordsScreen(
                    patientId = PatientSession.id,
                    onNavigateBack = {
                        navController.navigateUp()
                    },
                    onNavigateToDetail = { _, recordId ->
                        navController.navigate(
                            MedRecordDetailDestination(
                                PatientSession.id.toString(),
                                recordId.toString()
                            )
                        )
                    },
                    showSnackbar = showSnackbar
                )
            }

            composable<MedRecordDetailDestination> { backStackEntry ->
                val destination = backStackEntry.toRoute() as MedRecordDetailDestination
                MedRecordDetailScreen(
                    patientId = destination.patientId.toInt(),
                    recordId = destination.recordId.toInt(),
                    onNavigateBack = {
                        navController.navigateUp()
                    },
                    showSnackbar = showSnackbar
                )
            }

            composable<PatientListDestination> {
                PatientListScreen(
                    onNavigateToDetail = { patientId ->
                        navController.navigate(PatientDetailDestination(patientId.toString()))
                    },
                    showSnackbar = showSnackbar
                )
            }

            composable<PatientDetailDestination> { backStackEntry ->
                val destination = backStackEntry.toRoute() as PatientDetailDestination
                PatientDetailScreen(
                    patientId = destination.patientId.toInt(),
                    onNavigateBack = {
                        navController.navigateUp()
                    },
                    showSnackbar = showSnackbar
                )
            }
        }
    }
}




