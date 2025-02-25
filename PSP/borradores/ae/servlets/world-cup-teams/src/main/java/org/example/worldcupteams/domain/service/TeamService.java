package org.example.worldcupteams.domain.service;

import org.example.worldcupteams.common.errors.TeamNotFoundException;
import org.example.worldcupteams.common.errors.UnauthorizedOperationException;
import org.example.worldcupteams.dao.TeamDao;
import org.example.worldcupteams.domain.model.Team;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {
    private final TeamDao teamDao;

    public TeamService(TeamDao teamDao) {
        this.teamDao = teamDao;
    }

    public List<Team> getAllTeams() {
        return teamDao.findAll();
    }

    public Team getTeamById(Long id) {
        return teamDao.findById(id)
                .orElseThrow(() -> new TeamNotFoundException("Team not found with id: " + id));
    }

    public void addTeam(Team team) {
        teamDao.save(team);
    }

    public void deleteTeam(Long id, String userRole) {
        Team team = getTeamById(id);
        if ("MANAGER".equals(userRole)) {
            teamDao.delete(team);
        } else if ("COACH".equals(userRole) && team.getPlayers().isEmpty()) {
            teamDao.delete(team);
        } else {
            throw new UnauthorizedOperationException("Unauthorized to delete team");
        }
    }

    public Team getTeamByName(String name) {
        return teamDao.findByName(name)
                .orElseThrow(() -> new TeamNotFoundException("Team not found with name: " + name));
    }
}

