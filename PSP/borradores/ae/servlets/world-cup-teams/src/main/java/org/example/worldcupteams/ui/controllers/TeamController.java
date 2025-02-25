package org.example.worldcupteams.ui.controllers;

import org.example.worldcupteams.domain.model.Team;
import org.example.worldcupteams.domain.service.TeamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
public class TeamController {
    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('MANAGER', 'COACH', 'PLAYER')")
    public ResponseEntity<List<Team>> getAllTeams() {
        return ResponseEntity.ok(teamService.getAllTeams());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER', 'COACH', 'PLAYER')")
    public ResponseEntity<Team> getTeamById(@PathVariable Long id) {
        return ResponseEntity.ok(teamService.getTeamById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Team> addTeam(@RequestBody Team team) {
        teamService.addTeam(team);
        return ResponseEntity.status(HttpStatus.CREATED).body(team);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER', 'COACH')")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long id, Authentication authentication) {
        String role = authentication.getAuthorities().stream().findFirst().get().getAuthority().replace("ROLE_", "");
        teamService.deleteTeam(id, role);
        return ResponseEntity.noContent().build();
    }
}

