package org.example.loginspring_adriansaavedra.domain.service;


import org.example.loginspring_adriansaavedra.dao.DaoJugadores;
import org.example.loginspring_adriansaavedra.domain.model.Player;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GestionJugadores {
    private final DaoJugadores daoJugadores;

    public GestionJugadores(DaoJugadores daoJugadores) {
        this.daoJugadores = daoJugadores;
    }

    public List<Player> getAllPlayers() {
        return daoJugadores.getAllPlayers();
    }

    public boolean addPlayer(Player player) {
        return daoJugadores.addPlayer(player);
    }


    public void updatePlayer(Player player) {
        daoJugadores.updatePlayer(player);
    }

    public void deletePlayer(String id) {
        daoJugadores.deletePlayer(Integer.parseInt(id));
    }

    public Player getPlayerById(String id) {
        return daoJugadores.getPlayerById(Integer.parseInt(id));
    }

}
