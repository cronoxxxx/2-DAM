package org.example.mundialrest.dao;

import org.example.mundialrest.domain.model.Player;
import org.example.mundialrest.domain.model.Team;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PlayerDao {
    private final Database database;

    public PlayerDao(Database database) {
        this.database = database;
    }

    public void save(Team team, Player player) {
        player.setId(database.getPlayerIdCounter());
        team.getPlayers().add(player);
    }

    public Optional<Player> findById(Team team, Long playerId) {
        return team.getPlayers().stream()
                .filter(player -> player.getId().equals(playerId))
                .findFirst();
    }

    public void delete(Team team, Player player) {
        team.getPlayers().remove(player);
    }

    public void update(Team team, Player player) {
        int index = team.getPlayers().indexOf(player);
        if (index != -1) {
            team.getPlayers().set(index, player);
        }
    }
}

