package org.example.services;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.example.dao.CompletedMissionsDao;
import org.example.dao.MissionsDao;
import org.example.dao.PlayersDao;
import org.example.model.error.GameError;
import org.example.model.xml.CompletedMissions;


public class CompletedMissionsService {
    private final CompletedMissionsDao completedMissionsDao;
    private final PlayersDao playersDao;
    private final MissionsDao missionsDao;

    @Inject
    public CompletedMissionsService(CompletedMissionsDao completedMissionsDao, PlayersDao playersDao, MissionsDao missionsDao) {
        this.completedMissionsDao = completedMissionsDao;
        this.missionsDao = missionsDao;
        this.playersDao = playersDao;
    }


    public Either<GameError, CompletedMissions> getAll() {
        return completedMissionsDao.getAll();
    }


    public Either<GameError, Integer> save(CompletedMissions completedMissionsDao) {
        return this.completedMissionsDao.save(completedMissionsDao);
    }


    public Either<GameError, Integer> save(int missionId, int playerId, int characterId) {
        return missionsDao.get(missionId)
                .flatMap(mission -> playersDao.get(playerId)
                        .flatMap(player -> {
                            if (player.getTokens() < mission.getTokens()) {
                                return Either.left(new GameError("The player does not have enough tokens"));
                            }

                            int newTokenAmount = player.getTokens() - mission.getTokens();
                            return playersDao.update(player, newTokenAmount)
                                    .flatMap(updateResult -> completedMissionsDao.save(missionId, characterId)
                                            .mapLeft(error -> {
                                                // Rollback the token update if saving the completed mission fails
                                                playersDao.update(player, player.getTokens());
                                                return new GameError("Error saving completed mission: " + error.getMessage());
                                            }))
                                    .mapLeft(error -> new GameError("Error updating player tokens: " + error.getMessage()));
                        }))
                .mapLeft(error -> new GameError("Error retrieving mission or player: " + error.getMessage()));
    }
}
