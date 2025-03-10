package com.example.recuperacion_adriansaavedra.ui.navigation

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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.finalexamenmoviles_adriansaavedra.ui.common.BottomBar
import com.example.finalexamenmoviles_adriansaavedra.ui.common.TopBar
import com.example.recuperacion_adriansaavedra.ui.pLogin.LoginScreen
import com.example.recuperacion_adriansaavedra.ui.pPrimeraLista.PrimeraListaScreen
import com.example.recuperacion_adriansaavedra.ui.pSegundaLista.SegundaListaScreen
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
    var isTopBarVisible by rememberSaveable { mutableStateOf(true) }

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
            AnimatedVisibility(
                visible = isTopBarVisible,
                enter = fadeIn() + slideInVertically(initialOffsetY = { -it }),
                exit = fadeOut() + slideOutVertically(targetOffsetY = { -it })
            ) {
                TopBar(
                    title = topBarTitle,
                    showBackButton = showBackButton,
                    onBackClick = { navController.navigateUp() }
                )
            }
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
                        //navController.navigate(AddFavoritePlayerDestination)
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
                isTopBarVisible = true
                topBarTitle = "Login"
                showBackButton = false
                isBottomBarVisible = false
                isBottomFabVisible = false

                LoginScreen(
                    navigateToHome = {
                        navController.navigate(PrimeraPantallaListaDestination)
//                        {
//                            popUpTo(LoginDestination) { inclusive = true }
//                        }
                    },
                    showSnackbar = showSnackbar
                )
            }

            composable<PrimeraPantallaListaDestination> {
                isTopBarVisible = true
                topBarTitle = "Primera Lista"
                showBackButton = false
                isBottomBarVisible = true
                isBottomFabVisible = false

                PrimeraListaScreen(
                    showSnackbar = showSnackbar,
                    onNavigateToDetail = {
                        // DURANTE EL EXAMEN: Implementar navegación al detalle si es necesario
                        // navController.navigate(DetalleItemDestination(it))
                    }
                )
            }

            composable<SegundaPantallaListaDestination> {
                isTopBarVisible = true
                topBarTitle = "Segunda Lista"
                showBackButton = false
                isBottomBarVisible = true
                isBottomFabVisible = false

                SegundaListaScreen(
                    showSnackbar = showSnackbar,
                    onNavigateToDetail = {
                        // DURANTE EL EXAMEN: Implementar navegación al detalle si es necesario
                        // navController.navigate(DetalleItemDestination(it))
                    }
                )
            }

        }
    }
}