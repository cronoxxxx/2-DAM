package org.example.mundialrest.dao;

import org.example.mundialrest.domain.model.Team;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TeamDao {
    private final Database database;

    public TeamDao(Database database) {
        this.database = database;
    }

    public List<Team> findAll() {
        return database.getTeams();
    }

    public Optional<Team> findById(Long id) {
        return database.getTeams().stream()
                .filter(team -> team.getId().equals(id))
                .findFirst();
    }

    public Optional<Team> findByName(String name) {
        return database.getTeams().stream()
                .filter(team -> team.getName().equals(name))
                .findFirst();
    }

    public void save(Team team) {
        team.setId(database.getTeamIdCounter());
        database.getTeams().add(team);
    }

    public void update(Team team) {
        int index = database.getTeams().indexOf(team);
        if (index != -1) {
            database.getTeams().set(index, team);
        }
    }

    public void delete(Team team) {
        database.getTeams().remove(team);
    }
}

