package com.example.playersapp_adriansaavedra.domain.usecases.favorites

import com.example.playersapp_adriansaavedra.data.FavoritePlayerRepository
import com.example.playersapp_adriansaavedra.data.remote.utils.PlayerNameRequest
import javax.inject.Inject

class AddFavoritePlayerUseCase @Inject constructor(private val favoritePlayerRepository: FavoritePlayerRepository) {
    suspend operator fun invoke(credentialId: Int, playerName: PlayerNameRequest) =
        favoritePlayerRepository.addFavoritePlayer(credentialId, playerName)

}