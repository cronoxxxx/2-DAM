package com.example.finalexamenmoviles_adriansaavedra.data.remote.utils


import com.google.gson.annotations.SerializedName

data class AuthenticationResponse(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("refresh_token")
    val refreshToken: String,
)