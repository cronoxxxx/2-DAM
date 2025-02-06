package com.example.consolesapp_adriansaavedra.ui.navigation

import androidx.compose.foundation.layout.padding
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
import com.example.consolesapp_adriansaavedra.ui.pantallaConsolas.ConsoleScreen
import com.example.consolesapp_adriansaavedra.ui.pantallaLogin.LoginScreen
import kotlinx.coroutines.launch


@Composable
fun Navigation() {
    val navController = rememberNavController()
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val showSnackbar: (String) -> Unit = { message ->
        coroutineScope.launch {
            snackbarHostState.showSnackbar(message, duration = SnackbarDuration.Short)
        }
    }
    Scaffold(snackbarHost = {
        SnackbarHost(hostState = snackbarHostState)
    }) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = LoginDestination,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<LoginDestination> {
                LoginScreen(
                    navigateToHome = {
                        navController.navigate(ConsoleListDestination)
                    },
                    showSnackbar = showSnackbar
                )
            }
            composable<ConsoleListDestination> {
                ConsoleScreen(
                    showSnackbar = showSnackbar,
                    onNavigateToDetail = { consoleId ->
                        navController.navigate(ConsoleDetailDestination(consoleId = consoleId))
                    }
                )
            }
        }
    }
}