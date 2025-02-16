package com.example.playersapp_adriansaavedra.data
import com.example.playersapp_adriansaavedra.data.remote.datasource.PlayersRemoteDataSource
import com.example.playersapp_adriansaavedra.data.remote.model.Player
import com.example.playersapp_adriansaavedra.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
class PlayerRepository @Inject constructor(
    private val playerRemoteDataSource: PlayersRemoteDataSource,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    suspend fun getPlayers() = withContext(dispatcher) {
        playerRemoteDataSource.getPlayers()
    }

    suspend fun getPlayer(id: String) = withContext(dispatcher) {
        playerRemoteDataSource.getPlayer(id)
    }

    suspend fun addPlayer(player: Player) = withContext(dispatcher) {
        playerRemoteDataSource.addPlayer(player)
    }

    suspend fun updatePlayer(id: String, player: Player) = withContext(dispatcher) {
        playerRemoteDataSource.updatePlayer(id, player)
    }

    suspend fun deletePlayer(id: String) = withContext(dispatcher) {
        playerRemoteDataSource.deletePlayer(id)
    }
}