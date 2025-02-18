package com.example.playersapp_adriansaavedra.data.remote.services

import com.example.playersapp_adriansaavedra.data.remote.utils.PlayerNameRequest
import com.example.playersapp_adriansaavedra.domain.model.Player
import com.example.playersapp_adriansaavedra.ui.Constantes
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FavoritePlayerService {
    @GET(Constantes.FAVORITES_BY_CREDENTIAL)
    suspend fun getFavoritePlayers(@Path(Constantes.CREDENTIAL_ID) credentialId: Int): Response<List<Player>>

    @GET(Constantes.FAVORITE_PLAYER)
    suspend fun getFavoritePlayer(
        @Path(Constantes.CREDENTIAL_ID) credentialId: Int,
        @Path(Constantes.PLAYER_ID) playerId: Int
    ): Response<Player>

    @POST(Constantes.FAVORITES_BY_CREDENTIAL)
    suspend fun addFavoritePlayer(
        @Path(Constantes.CREDENTIAL_ID) credentialId: Int,
        @Body playerNameRequest: PlayerNameRequest
    ): Response<Unit>

    @DELETE(Constantes.FAVORITE_PLAYER)
    suspend fun deleteFavoritePlayer(
        @Path(Constantes.CREDENTIAL_ID) credentialId: Int,
        @Path(Constantes.PLAYER_ID) playerId: Int
    ): Response<Unit>
}
