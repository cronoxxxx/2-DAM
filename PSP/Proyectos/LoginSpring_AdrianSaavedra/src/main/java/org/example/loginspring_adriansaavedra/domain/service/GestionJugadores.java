package org.example.loginspring_adriansaavedra.domain.service;


import org.example.loginspring_adriansaavedra.dao.DaoJugadores;
import org.example.loginspring_adriansaavedra.domain.model.Player;
import org.example.loginspring_adriansaavedra.domain.validators.PlayerValidator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GestionJugadores {
    private final DaoJugadores daoJugadores;
    private final PlayerValidator playerValidator;

    public GestionJugadores(DaoJugadores daoJugadores, PlayerValidator playerValidator) {
        this.playerValidator = playerValidator;
        this.daoJugadores = daoJugadores;
    }

    public List<Player> getAllPlayers() {
        return daoJugadores.getAllPlayers();
    }

    public void addPlayer(Player player) {
        if (playerValidator.validatePlayer(player)) {
            daoJugadores.addPlayer(player);
        }
    }

    public void updatePlayer(Player player) {
        if (playerValidator.validatePlayer(player)) {
            daoJugadores.updatePlayer(player);
        }
    }

    public void deletePlayer(String id) {
        daoJugadores.deletePlayer(Integer.parseInt(id));
    }

    public Player getPlayerById(String id) {
        return daoJugadores.getPlayerById(Integer.parseInt(id));
    }
}
