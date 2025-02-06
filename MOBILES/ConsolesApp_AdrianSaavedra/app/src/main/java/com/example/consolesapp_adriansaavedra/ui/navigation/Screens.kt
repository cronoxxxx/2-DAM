package com.example.consolesapp_adriansaavedra.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
data object LoginDestination

@Serializable
data object ConsoleListDestination

@Serializable
data class ConsoleDetailDestination(val consoleId:Int)

