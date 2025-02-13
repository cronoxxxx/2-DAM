package com.example.borradorcompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.borradorcompose.DetailScreen
import com.example.borradorcompose.HomeScreen
import com.example.borradorcompose.LoginScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = MainDestination
    ) {
        composable<MainDestination> {
            LoginScreen {
                navController.navigate(HomeDestination)
            }
        }
        composable<HomeDestination> {
            HomeScreen { value ->
                navController.navigate(DetailDestination(value))
            }
        }
        //aqui recibe el parametro recibido del home screen
        composable<DetailDestination> { backStackEntry ->
            val detail = backStackEntry.toRoute() as DetailDestination
            DetailScreen(detail.name){
                navController.navigateUp() //recomendable

//                navController.popBackStack()
//                navController.navigate(MainDestination){popUpto<Login>{inclusive = true}} -- agregar el pop up to solo si se quiere limpiar 100%
            }
        }
    }
}
