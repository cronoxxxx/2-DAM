package com.example.consolesapp_adriansaavedra.ui.common

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.consolesapp_adriansaavedra.ui.Constantes
import com.example.consolesapp_adriansaavedra.ui.navigation.ConsoleListDestination
import com.example.consolesapp_adriansaavedra.ui.navigation.PlayerListDestination


@Composable
fun BottomBar(
    navController: NavController,
    items: List<BottomNavItem>
) {
    val infiniteTransition = rememberInfiniteTransition(label = Constantes.INF_TRANS_LABEL)
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = Constantes.ROTATION_ANGLE_LABEL
    )

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        item.icon,
                        contentDescription = item.title,
                        modifier = Modifier.rotate(rotation)
                    )
                },
                label = { Text(item.title) },
                selected = currentDestination?.route == item.destination.toString(),
                onClick = {
                    when (item.destination) {
                        is ConsoleListDestination -> navController.navigate(ConsoleListDestination) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }

                            launchSingleTop = true
                            restoreState = true
                        }

                        is PlayerListDestination -> navController.navigate(PlayerListDestination) {
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