package com.example.playersapp_adriansaavedra.data.remote.utils

import com.example.playersapp_adriansaavedra.ui.Constantes
import com.google.gson.annotations.SerializedName

data class AuthenticationResponse(
    @SerializedName(Constantes.AT_JSON)
    val accessToken: String,
    @SerializedName(Constantes.RT_JSON)
    val refreshToken: String,
)