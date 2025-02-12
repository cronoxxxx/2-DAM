package org.example.loginspring_adriansaavedra.dao;


import org.example.loginspring_adriansaavedra.domain.model.Player;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DaoJugadores {
    private final Database jugadores;


    public DaoJugadores(Database jugadores) {
        this.jugadores = jugadores;
    }

    public List<Player> getAllPlayers() {
        return new ArrayList<>(jugadores.getPlayers());
    }

    public boolean addPlayer(Player player) {
        boolean exists = jugadores.getPlayers().stream()
                .anyMatch(playerIter -> playerIter.getName().strip().equalsIgnoreCase(player.getName().strip()));

        if (exists) {
            return false;
        }

        int maxId = jugadores.getPlayers().stream()
                .mapToInt(Player::getId)
                .max()
                .orElse(0) + 1;

        player.setId(maxId);
        jugadores.getPlayers().add(player);
        return true;
    }


    public boolean updatePlayer(Player updatedPlayer) {
        boolean playerExists = jugadores.getPlayers().removeIf(player -> player.getId() == updatedPlayer.getId());
        if (playerExists) {
            jugadores.getPlayers().add(updatedPlayer);
        }
        return playerExists;
    }

    public boolean deletePlayer(int id) {
        return jugadores.getPlayers().removeIf(player -> player.getId() == id);
    }

    public Player getPlayerById(int id) {
        return jugadores.getPlayers().stream()
                .filter(player -> player.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
