package org.example.loginspring_adriansaavedra.ui.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.example.loginspring_adriansaavedra.common.Constantes;
import org.example.loginspring_adriansaavedra.domain.model.Player;
import org.example.loginspring_adriansaavedra.domain.service.GestionJugadoresFavoritos;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constantes.FAVORITES_DIR)
public class FavoritePlayersController {

    private final GestionJugadoresFavoritos gestionJugadoresFavoritos;

    public FavoritePlayersController(GestionJugadoresFavoritos gestionJugadoresFavoritos) {
        this.gestionJugadoresFavoritos = gestionJugadoresFavoritos;
    }

    @GetMapping("/{credentialId}")
    public ResponseEntity<List<Player>> getFavoritePlayers(@PathVariable int credentialId) {
        List<Player> favoritePlayers = gestionJugadoresFavoritos.getPlayersForCredential(credentialId);
        return ResponseEntity.status(HttpServletResponse.SC_OK).body(favoritePlayers);
    }

    @GetMapping("/{credentialId}/{playerId}")
    public ResponseEntity<Player> getFavoritePlayer(@PathVariable int credentialId, @PathVariable int playerId) {
        Player player = gestionJugadoresFavoritos.getPlayerForCredential(credentialId, playerId);
        return ResponseEntity.status(HttpServletResponse.SC_OK).body(player);
    }

    @PostMapping("/{credentialId}/{playerId}")
    public ResponseEntity<Void> addFavoritePlayer(@PathVariable int credentialId, @PathVariable int playerId) {
        gestionJugadoresFavoritos.addPlayerForCredential(credentialId, playerId);
        return ResponseEntity.status(HttpServletResponse.SC_CREATED).build();
    }

    @DeleteMapping("/{credentialId}/{playerId}")
    public ResponseEntity<Void> deleteFavoritePlayer(@PathVariable int credentialId, @PathVariable int playerId) {
        gestionJugadoresFavoritos.deletePlayerForCredential(credentialId, playerId);
        return ResponseEntity.status(HttpServletResponse.SC_NO_CONTENT).build();
    }
}