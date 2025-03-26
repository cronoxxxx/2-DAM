package com.example.finalexamenmoviles_adriansaavedra.ui.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.finalexamenmoviles_adriansaavedra.data.remote.utils.TokenExpirationHandler
import com.example.finalexamenmoviles_adriansaavedra.ui.common.BottomBar
import com.example.finalexamenmoviles_adriansaavedra.ui.common.TopBar
import com.example.finalexamenmoviles_adriansaavedra.ui.pantallaAlumnos.AlumnosScreen
import com.example.finalexamenmoviles_adriansaavedra.ui.pantallaDetalleInformes.InformeDetailScreen
import com.example.finalexamenmoviles_adriansaavedra.ui.pantallaInformes.InformesScreen
import com.example.finalexamenmoviles_adriansaavedra.ui.pantallaLogin.LoginScreen
import com.example.finalexamenmoviles_adriansaavedra.ui.pantallaRatones.RatonScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@Composable
fun Navigation(viewModel: NavigationViewModel = hiltViewModel()) {
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

    LaunchedEffect(Unit) {
        viewModel.tokenExpiredEvents.collect {
            navController.navigate(LoginDestination) {
                popUpTo(navController.graph.id) {  inclusive = true  }
            }
        }
    }



    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            AnimatedVisibility(
                visible = isTopBarVisible,
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
                isTopBarVisible = true
                topBarTitle = "login screen title"
                showBackButton = false
                isBottomBarVisible = false
                isBottomFabVisible = false
                LoginScreen(navigateToHome = {
                    navController.navigate(
                        HomeDestination
                    )
                },showSnackbar = showSnackbar)
            }
            composable<HomeDestination> {
                isTopBarVisible = true
                topBarTitle = "pantalla de alumnos"
                showBackButton = false
                isBottomBarVisible = true
                isBottomFabVisible = false
                AlumnosScreen(showSnackbar = showSnackbar)

            }
            composable<RatDestination> {
                isTopBarVisible = false
                showBackButton = true
                isBottomBarVisible = false
                isBottomFabVisible = false
                RatonScreen(showSnackbar = showSnackbar,
                    onNavigateBack = {
                        navController.navigate(HomeDestination) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }

                        }
                    })
            }

            composable<InformesDestination> {
                isTopBarVisible = true
                topBarTitle = "Informes"
                showBackButton = false
                isBottomBarVisible = true
                isBottomFabVisible = false
                InformesScreen(
                    showSnackbar = showSnackbar,
                    onNavigateToDetail = {
                        navController.navigate(InformeDetails(it))
                    }
                )
            }

            composable<InformeDetails> { backStackEntry ->
                val destination = backStackEntry.toRoute() as InformeDetails
                isTopBarVisible = true
                topBarTitle = "Detalle Informe"
                showBackButton = true
                isBottomBarVisible = false
                isBottomFabVisible = false
                InformeDetailScreen(showSnackbar, destination.id)
            }
        }

    }
}









@HiltViewModel
class NavigationViewModel @Inject constructor(
    tokenExpirationHandler: TokenExpirationHandler
) : ViewModel() {
    val tokenExpiredEvents = tokenExpirationHandler.tokenExpired
}





