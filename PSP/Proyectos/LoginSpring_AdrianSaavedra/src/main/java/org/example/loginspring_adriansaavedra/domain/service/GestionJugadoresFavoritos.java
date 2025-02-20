package org.example.loginspring_adriansaavedra.domain.service;

import org.example.loginspring_adriansaavedra.dao.DaoJugadoresFavoritos;
import org.example.loginspring_adriansaavedra.domain.model.PlayerEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GestionJugadoresFavoritos {
    private final DaoJugadoresFavoritos daoJugadoresFavoritos;

    public GestionJugadoresFavoritos(DaoJugadoresFavoritos daoJugadoresFavoritos) {
        this.daoJugadoresFavoritos = daoJugadoresFavoritos;
    }

    public List<PlayerEntity> getPlayersForCredential(int credentialId) {
        return daoJugadoresFavoritos.getPlayersForCredential(credentialId);
    }

    public PlayerEntity getPlayerForCredential(int credentialId, int playerId) {
        return daoJugadoresFavoritos.getPlayerForCredential(credentialId, playerId);
    }
    @Transactional
    public PlayerEntity addPlayerForCredential(int credentialId, String playerName) {
        return daoJugadoresFavoritos.addPlayerForCredential(credentialId, playerName);
    }
    @Transactional
    public void deletePlayerForCredential(int credentialId, int playerId) {
        daoJugadoresFavoritos.deletePlayerForCredential(credentialId, playerId);
    }
}
