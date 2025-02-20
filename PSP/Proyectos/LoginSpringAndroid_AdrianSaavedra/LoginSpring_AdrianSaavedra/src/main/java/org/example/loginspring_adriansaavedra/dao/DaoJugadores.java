package org.example.loginspring_adriansaavedra.dao;


import org.example.loginspring_adriansaavedra.common.Constantes;
import org.example.loginspring_adriansaavedra.common.errors.PlayerNotFoundException;
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

    public Player getPlayerById(int id) {
        return jugadores.getPlayers().stream()
                .filter(player -> player.getId() == id)
                .findFirst()
                .orElseThrow(() -> new PlayerNotFoundException(Constantes.PLAYER_NOT_FOUND));
    }
}
