package com.example.playersapp_adriansaavedra.domain.usecases.favorites

import com.example.playersapp_adriansaavedra.data.FavoritePlayerRepository
import javax.inject.Inject

class DeleteFavoritePlayerUseCase @Inject constructor(private val favoritePlayerRepository: FavoritePlayerRepository) {
    suspend operator fun invoke(credentialId: Int, playerId: Int) =
        favoritePlayerRepository.deleteFavoritePlayer(credentialId, playerId)

}