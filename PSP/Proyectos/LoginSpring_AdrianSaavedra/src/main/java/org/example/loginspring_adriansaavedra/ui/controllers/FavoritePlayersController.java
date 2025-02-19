package org.example.loginspring_adriansaavedra.ui.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.example.loginspring_adriansaavedra.common.Constantes;
import org.example.loginspring_adriansaavedra.domain.model.PlayerEntity;
import org.example.loginspring_adriansaavedra.domain.service.GestionJugadoresFavoritos;
import org.example.loginspring_adriansaavedra.ui.utils.PlayerNameRequest;
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

    @GetMapping(Constantes.FAV_PLAYERS_ONE_DIR)
    public ResponseEntity<List<PlayerEntity>> getFavoritePlayers(@PathVariable int credentialId) {
        List<PlayerEntity> favoritePlayerEntities = gestionJugadoresFavoritos.getPlayersForCredential(credentialId);
        return ResponseEntity.status(HttpServletResponse.SC_OK).body(favoritePlayerEntities);
    }

    @GetMapping(Constantes.FAV_PLAYERS_TWO_DIR)
    public ResponseEntity<PlayerEntity> getFavoritePlayer(@PathVariable int credentialId, @PathVariable int playerId) {
        PlayerEntity playerEntity = gestionJugadoresFavoritos.getPlayerForCredential(credentialId, playerId);
        return ResponseEntity.status(HttpServletResponse.SC_OK).body(playerEntity);
    }

    @PostMapping(Constantes.FAV_PLAYERS_ONE_DIR)
    public ResponseEntity<Void> addFavoritePlayer(@PathVariable int credentialId, @RequestBody PlayerNameRequest request) {
        gestionJugadoresFavoritos.addPlayerForCredential(credentialId, request.getPlayerName());
        return ResponseEntity.status(HttpServletResponse.SC_CREATED).build();
    }


    @DeleteMapping(Constantes.FAV_PLAYERS_TWO_DIR)
    public ResponseEntity<Void> deleteFavoritePlayer(@PathVariable int credentialId, @PathVariable int playerId) {
        gestionJugadoresFavoritos.deletePlayerForCredential(credentialId, playerId);
        return ResponseEntity.status(HttpServletResponse.SC_NO_CONTENT).build();
    }
}