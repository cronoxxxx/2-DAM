package org.example.domain.service;

import jakarta.inject.Inject;
import org.example.dao.DaoJugadores;
import org.example.domain.model.Player;

import java.util.List;

public class GestionJugadores {
    private final DaoJugadores daoJugadores;

    @Inject
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
