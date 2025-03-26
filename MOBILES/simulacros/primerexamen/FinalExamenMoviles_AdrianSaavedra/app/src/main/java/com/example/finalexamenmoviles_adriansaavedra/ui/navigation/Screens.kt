package com.example.finalexamenmoviles_adriansaavedra.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.example.finalexamenmoviles_adriansaavedra.ui.common.BottomNavItem
import kotlinx.serialization.Serializable

@Serializable
data object LoginDestination

@Serializable
data object HomeDestination

@Serializable
data object RatDestination

@Serializable
data object InformesDestination

@Serializable
data class InformeDetails(val id: Int)

@Composable
fun rememberBottomNavItems(): List<BottomNavItem> {
    val playersTitle = "Alumnos"

    return remember {
        listOf(
            BottomNavItem(
                title = playersTitle,
                destination = HomeDestination,
                icon = Icons.Filled.Person
            ),BottomNavItem(
                title = "Ratas",
                destination = RatDestination,
                icon = Icons.Filled.DateRange
            ),
            BottomNavItem(
                title = "Informes",
                destination = InformesDestination,
                icon = Icons.Filled.Star
            ),
        )
    }
}
