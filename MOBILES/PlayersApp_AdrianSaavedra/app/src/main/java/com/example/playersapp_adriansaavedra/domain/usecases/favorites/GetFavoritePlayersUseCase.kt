package com.example.playersapp_adriansaavedra.domain.usecases.favorites

import com.example.playersapp_adriansaavedra.data.FavoritePlayerRepository
import com.example.playersapp_adriansaavedra.data.remote.services.FavoritePlayerService
import com.example.playersapp_adriansaavedra.domain.model.Player
import javax.inject.Inject

class GetFavoritePlayersUseCase @Inject constructor(private val favoritePlayerRepository: FavoritePlayerRepository) {
    suspend operator fun invoke(credentialId: Int) =
        favoritePlayerRepository.getFavoritePlayers(credentialId)

}
