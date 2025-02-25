package org.example.worldcupteams.ui.controllers;

import org.example.worldcupteams.domain.model.Player;
import org.example.worldcupteams.domain.service.PlayerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teams/{teamName}/players")
public class PlayerController {
    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping
    @PreAuthorize("hasRole('COACH')")
    public ResponseEntity<Void> addPlayer(@PathVariable String teamName, @RequestBody Player player) {
        playerService.addPlayer(teamName, player);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{playerId}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Void> updatePlayer(@PathVariable String teamName, @PathVariable Long playerId, @RequestParam String newName) {
        playerService.updatePlayer(teamName, playerId, newName);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{playerId}")
    @PreAuthorize("hasAnyRole('MANAGER', 'COACH')")
    public ResponseEntity<Void> deletePlayer(@PathVariable String teamName, @PathVariable Long playerId, Authentication authentication) {
        String role = authentication.getAuthorities().stream().findFirst().get().getAuthority().replace("ROLE_", "");
        playerService.deletePlayer(teamName, playerId, role);
        return ResponseEntity.noContent().build();
    }
}

