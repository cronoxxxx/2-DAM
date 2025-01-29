package com.example.hospitalapp_adriansaavedra.ui.common

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.hospitalapp_adriansaavedra.ui.navigation.MedRecordListDestination
import com.example.hospitalapp_adriansaavedra.ui.navigation.PatientListDestination

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
                        is MedRecordListDestination -> navController.navigate(
                            MedRecordListDestination
                        )

                        is PatientListDestination -> navController.navigate(PatientListDestination)
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