package org.example.loginspring_adriansaavedra.domain.service;


import org.example.loginspring_adriansaavedra.dao.DaoJugadores;
import org.example.loginspring_adriansaavedra.domain.model.PlayerEntity;
import org.example.loginspring_adriansaavedra.domain.validators.PlayerValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GestionJugadores {
    private final DaoJugadores daoJugadores;
    private final PlayerValidator playerValidator;

    public GestionJugadores(DaoJugadores daoJugadores, PlayerValidator playerValidator) {
        this.daoJugadores = daoJugadores;
        this.playerValidator = playerValidator;
    }

    public List<PlayerEntity> getAllPlayers() {
        return daoJugadores.getAllPlayers();
    }

    @Transactional
    public void addPlayer(PlayerEntity playerEntity) {
        if (playerValidator.validatePlayer(playerEntity)) {
            daoJugadores.addPlayer(playerEntity);
        }
    }

    @Transactional
    public void updatePlayer(PlayerEntity playerEntity) {
        if (playerValidator.validatePlayer(playerEntity)) {
            daoJugadores.updatePlayer(playerEntity);
        }
    }

    @Transactional
    public void deletePlayer(String id) {
        daoJugadores.deletePlayer(Integer.parseInt(id));
    }

    public PlayerEntity getPlayerById(String id) {
        return daoJugadores.getPlayerById(Integer.parseInt(id));
    }
}
