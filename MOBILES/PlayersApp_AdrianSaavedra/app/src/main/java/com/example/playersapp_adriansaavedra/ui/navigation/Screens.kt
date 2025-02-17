package com.example.playersapp_adriansaavedra.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import com.example.playersapp_adriansaavedra.ui.common.BottomNavItem
import kotlinx.serialization.Serializable

@Serializable
data object LoginDestination

@Serializable
data object PlayersDestination

@Serializable
data object FavoritePlayersDestination

@Serializable
data class FavoritePlayerDetailsDestination(val playerId: Int)

@Serializable
data class PlayerDetailsDestination(val playerId: Int)

@Serializable
data object AddFavoritePlayerDestination


@Composable
fun rememberBottomNavItems(): List<BottomNavItem> {
    val playersTitle = "Players"
    val favoritesTitle = "Favorites"

    return remember {
        listOf(
            BottomNavItem(
                title = playersTitle,
                destination = PlayersDestination,
                icon = Icons.Filled.Person
            ),
            BottomNavItem(
                title = favoritesTitle,
                destination = FavoritePlayersDestination,
                icon = Icons.Filled.Star
            )
        )
    }
}
