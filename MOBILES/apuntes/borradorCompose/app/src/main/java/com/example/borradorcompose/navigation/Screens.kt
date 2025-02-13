package com.example.borradorcompose.navigation

import kotlinx.serialization.Serializable

@Serializable
object MainDestination

@Serializable
object HomeDestination

@Serializable
data class DetailDestination(val name:String)
