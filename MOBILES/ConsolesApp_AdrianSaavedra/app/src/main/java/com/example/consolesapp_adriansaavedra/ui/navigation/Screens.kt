package com.example.consolesapp_adriansaavedra.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
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
data class ConsoleDetailDestination(val consoleId: Int)

@Serializable
data object AddConsoleDestination

@Serializable
data object PlayerListDestination

@Serializable
data class PlayerDetailDestination(val playerId: Int)


@Composable
fun rememberBottomNavItems(): List<BottomNavItem> {
    val consolesTitle = stringResource(R.string.consoles)
    val playersTitle = stringResource(R.string.players)
    val consoleIcon = ImageVector.vectorResource(id = R.drawable.game_icon)

    return remember {
        listOf(
            BottomNavItem(
                title = consolesTitle,
                destination = ConsoleListDestination,
                icon = consoleIcon
            ),
            BottomNavItem(
                title = playersTitle,
                destination = PlayerListDestination,
                icon = Icons.Filled.Person
            )
        )
    }
}

