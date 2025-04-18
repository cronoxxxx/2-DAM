package com.example.consolesapp_adriansaavedra.ui.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.consolesapp_adriansaavedra.R
import com.example.consolesapp_adriansaavedra.ui.common.BottomBar
import com.example.consolesapp_adriansaavedra.ui.common.TopBar
import com.example.consolesapp_adriansaavedra.ui.pantallaAgregarConsola.AddConsoleScreen
import com.example.consolesapp_adriansaavedra.ui.pantallaConsolaDetalle.ConsoleDetailScreen
import com.example.consolesapp_adriansaavedra.ui.pantallaConsolas.ConsolesScreen
import com.example.consolesapp_adriansaavedra.ui.pantallaJugadorDetalle.PlayerDetailScreen
import com.example.consolesapp_adriansaavedra.ui.pantallaJugadores.PlayersScreen
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
    var isBottomFabVisible by rememberSaveable { mutableStateOf(false) }

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
        }, floatingActionButton = {
            AnimatedVisibility(visible = isBottomFabVisible) {
                FloatingActionButton(
                    onClick = {
                        navController.navigate(AddConsoleDestination)
                    }
                ) {
                    Icon(imageVector = Icons.Rounded.Add, contentDescription = null)
                }
            }
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
                isBottomBarVisible = false
                isBottomFabVisible = false
                LoginScreen(
                    navigateToHome = {
                        navController.navigate(ConsoleListDestination)
                    },
                    showSnackbar = showSnackbar
                )
            }
            composable<ConsoleListDestination> {
                topBarTitle = stringResource(R.string.consoles)
                showBackButton = false
                isBottomBarVisible = true
                isBottomFabVisible = true

                ConsolesScreen(
                    showSnackbar = showSnackbar,
                    onNavigateToDetail = { consoleId ->
                        navController.navigate(ConsoleDetailDestination(consoleId = consoleId))
                    }
                )
            }
            composable<ConsoleDetailDestination> { backStackEntry ->
                val destination = backStackEntry.toRoute() as ConsoleDetailDestination
                topBarTitle = stringResource(R.string.console) + destination.consoleId
                showBackButton = true
                isBottomBarVisible = true
                isBottomFabVisible = false
                ConsoleDetailScreen(
                    showSnackbar = showSnackbar,
                    onNavigateBack = { navController.navigateUp() },
                    consoleId = destination.consoleId
                )
            }
            composable<AddConsoleDestination> {
                topBarTitle = stringResource(R.string.add_console)
                showBackButton = true
                isBottomBarVisible = true
                isBottomFabVisible = false
                AddConsoleScreen(
                    onNavigateBack = {
                        navController.navigateUp()
                    },
                    showSnackbar = showSnackbar
                )
            }
            composable<PlayerListDestination> {
                topBarTitle = stringResource(R.string.players)
                showBackButton = false
                isBottomBarVisible = true
                isBottomFabVisible = false
                PlayersScreen(
                    showSnackbar = showSnackbar,
                    onNavigateToDetail = { playerId ->
                        navController.navigate(
                            PlayerDetailDestination(playerId = playerId)
                        )
                    })
            }
            composable<PlayerDetailDestination> { backStackEntry ->
                val destination = backStackEntry.toRoute() as PlayerDetailDestination
                topBarTitle = stringResource(R.string.player) + destination.playerId
                showBackButton = true
                isBottomBarVisible = true
                isBottomFabVisible = false
                PlayerDetailScreen(
                    showSnackbar = showSnackbar,
                    onNavigateBack = { navController.navigateUp() },
                    playerId = destination.playerId
                )
            }
        }
    }
}
//crear un remembersaveable de topbarstate
//sealed class de topbarstate

//crear object tque sean de :TopBarState