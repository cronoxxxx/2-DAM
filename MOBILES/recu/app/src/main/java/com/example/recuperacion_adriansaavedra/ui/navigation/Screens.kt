package com.example.recuperacion_adriansaavedra.ui.navigation

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
data object PrimeraPantallaListaDestination

@Serializable
data object SegundaPantallaListaDestination


@Composable
fun rememberBottomNavItems(): List<BottomNavItem> {
    val playersTitle = "Alumnos"

    return remember {
        listOf(
            BottomNavItem(
                title = playersTitle,
                destination = PrimeraPantallaListaDestination,
                icon = Icons.Filled.Person
            ),BottomNavItem(
                title = "Ratas",
                destination = SegundaPantallaListaDestination,
                icon = Icons.Filled.DateRange
            ),

        )
    }
}