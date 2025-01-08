package com.example.examfirstmoviles_adriansaavedra.domain.usecases.equipo

import com.example.examfirstmoviles_adriansaavedra.data.PlayersRepository
import javax.inject.Inject

class GetPlayersUseCase @Inject constructor(private val playersRepository: PlayersRepository) {
    suspend operator fun invoke(id : Int) = playersRepository.fetchPlayers(id)
}