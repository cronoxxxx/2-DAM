package org.example.loginspring_adriansaavedra.ui.controllers;

import org.example.loginspring_adriansaavedra.domain.model.Player;
import org.example.loginspring_adriansaavedra.domain.service.GestionJugadores;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/players")

public class PlayersController {

    private final GestionJugadores gestionJugadores;

    public PlayersController(GestionJugadores gestionJugadores) {
        this.gestionJugadores = gestionJugadores;
    }

    @GetMapping
    public ResponseEntity<List<Player>> getPlayers() {
        return ResponseEntity.ok(gestionJugadores.getAllPlayers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Player> getPlayer(@PathVariable String id) {
        Player player = gestionJugadores.getPlayerById(id);
        if (player != null) {
            return ResponseEntity.ok(player);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Player> addPlayer(@RequestBody Player player) {
        boolean added = gestionJugadores.addPlayer(player);
        if (added) {
            return ResponseEntity.status(HttpStatus.CREATED).body(player);
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Player> updatePlayer(@PathVariable String id, @RequestBody Player player) {
        player.setId(Integer.parseInt(id));
        gestionJugadores.updatePlayer(player);
        return ResponseEntity.ok(player);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayer(@PathVariable String id) {
        gestionJugadores.deletePlayer(id);
        return ResponseEntity.noContent().build();
    }
}