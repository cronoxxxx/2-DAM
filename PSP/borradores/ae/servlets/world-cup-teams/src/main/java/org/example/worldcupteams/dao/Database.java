package org.example.worldcupteams.dao;

import lombok.Getter;
import org.example.worldcupteams.domain.model.Team;
import org.example.worldcupteams.domain.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Component
public class Database {
    private long playerIdCounter = 1;
    private long teamIdCounter = 1;
    private final List<Team> teams;
    private final List<User> users;

    public Database() {
        this.teams = new ArrayList<>();
        this.users = new ArrayList<>();
    }

    public long getPlayerIdCounter() {
        return playerIdCounter++;
    }

    public long getTeamIdCounter() {
        return teamIdCounter++;
    }
}

