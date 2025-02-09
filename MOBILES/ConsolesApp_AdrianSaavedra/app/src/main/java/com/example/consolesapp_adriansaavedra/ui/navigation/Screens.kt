package com.example.consolesapp_adriansaavedra.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import com.example.consolesapp_adriansaavedra.R
import com.example.consolesapp_adriansaavedra.ui.common.BottomNavItem
import kotlinx.serialization.Serializable

@Serializable
data object LoginDestination

@Serializable
data object ConsoleListDestination

@Serializable
data class ConsoleDetailDestination(val consoleId:Int)


@Composable
fun rememberBottomNavItems(): List<BottomNavItem> {
//    val recordsTitle = stringResource(R.string.records)
//    val patientsTitle = stringResource(R.string.patients)
    val consoleIcon = ImageVector.vectorResource(id = R.drawable.game_icon)

    return remember {
        listOf(
            BottomNavItem(
                title = "Consoles",
                destination = ConsoleListDestination,
                icon = consoleIcon
            )
        )
    }
}

