package com.example.consolesapp_adriansaavedra.ui.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.*
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.consolesapp_adriansaavedra.ui.common.BottomBar
import com.example.consolesapp_adriansaavedra.ui.common.TopBar
import com.example.consolesapp_adriansaavedra.ui.pantallaConsolaDetalle.ConsoleDetailScreen
import com.example.consolesapp_adriansaavedra.ui.pantallaConsolas.ConsolesScreen
import com.example.consolesapp_adriansaavedra.ui.pantallaLogin.LoginScreen
import kotlinx.coroutines.launch


@Composable
fun Navigation() {
    val navController = rememberNavController()
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    var topBarTitle by rememberSaveable { mutableStateOf("") }
    var showBackButton by rememberSaveable { mutableStateOf(false) }
    var isBottomBarVisible by rememberSaveable { mutableStateOf(true) }

    val showSnackbar: (String) -> Unit = { message ->
        coroutineScope.launch {
            snackbarHostState.showSnackbar(message, duration = SnackbarDuration.Short)
        }
    }
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            TopBar(
                title = topBarTitle,
                showBackButton = showBackButton,
                onBackClick = { navController.navigateUp() }
            )
        },
        bottomBar = {
            AnimatedVisibility(
                visible = isBottomBarVisible,
                enter = fadeIn() + slideInVertically(initialOffsetY = { it }),
                exit = fadeOut() + slideOutVertically(targetOffsetY = { it })
            ) {
                BottomBar(navController = navController, items = rememberBottomNavItems())
            }
        }

    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = LoginDestination,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<LoginDestination> {
                topBarTitle = "Login"
                showBackButton = false
                isBottomBarVisible = false
                LoginScreen(
                    navigateToHome = {
                        navController.navigate(ConsoleListDestination)
                    },
                    showSnackbar = showSnackbar
                )
            }
            composable<ConsoleListDestination> {
                topBarTitle = "Consolas"
                showBackButton = false
                isBottomBarVisible = true

                ConsolesScreen(
                    showSnackbar = showSnackbar,
                    onNavigateToDetail = { consoleId ->
                        navController.navigate(ConsoleDetailDestination(consoleId = consoleId))
                    }
                )
            }
            composable<ConsoleDetailDestination> { backStackEntry ->
                val destination = backStackEntry.toRoute() as ConsoleDetailDestination
                topBarTitle = "Consola" + destination.consoleId
                showBackButton = true
                isBottomBarVisible = true
                ConsoleDetailScreen(
                    showSnackbar = showSnackbar,
                    onNavigateBack = { navController.popBackStack() },
                    consoleId = destination.consoleId
                )
            }
        }
    }
}