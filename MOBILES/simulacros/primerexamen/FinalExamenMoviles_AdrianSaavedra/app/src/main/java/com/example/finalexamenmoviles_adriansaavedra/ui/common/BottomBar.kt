package com.example.finalexamenmoviles_adriansaavedra.ui.common

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.finalexamenmoviles_adriansaavedra.ui.navigation.HomeDestination
import com.example.finalexamenmoviles_adriansaavedra.ui.navigation.InformesDestination
import com.example.finalexamenmoviles_adriansaavedra.ui.navigation.RatDestination

@Composable
fun BottomBar(
    navController: NavController,
    items: List<BottomNavItem>
) {
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(item.title) },
                selected = currentDestination?.route == item.destination.toString(),
                onClick = {
                    when (item.destination) {
                        is HomeDestination -> navController.navigate(HomeDestination) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }

                            launchSingleTop = true
                            restoreState = true
                        }
                        is RatDestination -> navController.navigate(RatDestination) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }

                            launchSingleTop = true
                            restoreState = true
                        }

                        is InformesDestination -> navController.navigate(InformesDestination) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }




                    }

                }
            )
        }
    }
}


data class BottomNavItem(
    val title: String,
    val destination: Any,
    val icon: ImageVector
)