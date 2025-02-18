package com.example.playersapp_adriansaavedra.ui.navigation

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
import com.example.playersapp_adriansaavedra.R
import com.example.playersapp_adriansaavedra.ui.common.BottomBar
import com.example.playersapp_adriansaavedra.ui.common.TopBar
import com.example.playersapp_adriansaavedra.ui.pantallaAgregarFavoritos.AddFavoritesScreen
import com.example.playersapp_adriansaavedra.ui.pantallaFavorito.FavoriteDetailScreen
import com.example.playersapp_adriansaavedra.ui.pantallaFavoritos.FavoritesScreen
import com.example.playersapp_adriansaavedra.ui.pantallaJugador.PlayerDetailScreen
import com.example.playersapp_adriansaavedra.ui.pantallaJugadores.PlayersScreen
import com.example.playersapp_adriansaavedra.ui.pantallaLogin.LoginScreen
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
        },
        floatingActionButton = {
            AnimatedVisibility(visible = isBottomFabVisible) {
                FloatingActionButton(
                    onClick = {
                        navController.navigate(AddFavoritePlayerDestination)
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
                topBarTitle = stringResource(R.string.login_screen_title)
                showBackButton = false
                isBottomBarVisible = false
                isBottomFabVisible = false
                LoginScreen(navigateToHome = {
                    navController.navigate(
                        PlayersDestination
                    )
                }, showSnackbar = showSnackbar)
            }
            composable<PlayersDestination> {
                isBottomBarVisible = true
                isBottomFabVisible = false
                topBarTitle = stringResource(R.string.players_list_title)
                showBackButton = false
                PlayersScreen(showSnackbar = showSnackbar, onNavigateToDetail = {
                    navController.navigate(PlayerDetailsDestination(it))
                })
            }

            composable<PlayerDetailsDestination> { backStackEntry ->
                val destination = backStackEntry.toRoute() as PlayerDetailsDestination
                isBottomBarVisible = true
                isBottomFabVisible = false
                topBarTitle = stringResource(R.string.player_detail_title)
                showBackButton = true
                PlayerDetailScreen(
                    playerId = destination.playerId,
                    showSnackbar = showSnackbar,
                )
            }

            composable<FavoritePlayerDetailsDestination> { backStackEntry ->
                val destination = backStackEntry.toRoute() as FavoritePlayerDetailsDestination
                isBottomBarVisible = true
                isBottomFabVisible = false
                topBarTitle = stringResource(R.string.favorite_player_screen_title)
                showBackButton = true
                FavoriteDetailScreen(
                    playerId = destination.playerId,
                    showSnackbar = showSnackbar,
                    onNavigateBack = {
                        navController.navigateUp()
                    }
                )

            }


            composable<FavoritePlayersDestination> {
                isBottomBarVisible = true
                isBottomFabVisible = true
                topBarTitle = stringResource(R.string.favorites_title)
                showBackButton = false
                FavoritesScreen(showSnackbar = showSnackbar, onNavigateToDetail = {
                    navController.navigate(FavoritePlayerDetailsDestination(it))
                })
            }

            composable<AddFavoritePlayerDestination> {
                isBottomBarVisible = false
                isBottomFabVisible = false
                topBarTitle = stringResource(R.string.add_favorite_player_title)
                showBackButton = true
                AddFavoritesScreen(
                    showSnackbar = showSnackbar,
                    onNavigateBack = {
                        navController.navigateUp()
                    }
                )
            }

        }
    }
}

