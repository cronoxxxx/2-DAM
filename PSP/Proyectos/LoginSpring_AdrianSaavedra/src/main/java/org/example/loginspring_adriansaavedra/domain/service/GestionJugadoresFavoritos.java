package org.example.loginspring_adriansaavedra.domain.service;

import org.example.loginspring_adriansaavedra.dao.DaoJugadoresFavoritos;
import org.example.loginspring_adriansaavedra.domain.model.PlayerEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
@Service
public class GestionJugadoresFavoritos {
    private final DaoJugadoresFavoritos daoJugadoresFavoritos;

    public GestionJugadoresFavoritos(DaoJugadoresFavoritos daoJugadoresFavoritos) {
        this.daoJugadoresFavoritos = daoJugadoresFavoritos;
    }

    public List<PlayerEntity> getPlayersForUser(String username) {
        return daoJugadoresFavoritos.getPlayersForUser(username);
    }

    public PlayerEntity getPlayerForUser(String username, int playerId) {
        return daoJugadoresFavoritos.getPlayerForUser(username, playerId);
    }

    public PlayerEntity addPlayerForUser(String username, String playerName) {
        return daoJugadoresFavoritos.addPlayerForUser(username, playerName);
    }

    public void deletePlayerForUser(String username, int playerId) {
        daoJugadoresFavoritos.deletePlayerForUser(username, playerId);
    }
}
