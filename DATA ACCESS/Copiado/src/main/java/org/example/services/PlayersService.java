package org.example.services;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.example.dao.PlayersDao;
import org.example.model.Player;
import org.example.model.error.GameError;

public class PlayersService{
    private final PlayersDao playersDao;

    @Inject
    public PlayersService(PlayersDao playersDao) {
        this.playersDao = playersDao;
    }

    public Either<GameError, Integer> save(Player p) {
        return this.playersDao.save(p);
    }
}

