package org.example.loginspring_adriansaavedra.ui.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.example.loginspring_adriansaavedra.common.Constantes;
import org.example.loginspring_adriansaavedra.domain.model.PlayerEntity;
import org.example.loginspring_adriansaavedra.domain.service.GestionJugadoresFavoritos;
import org.example.loginspring_adriansaavedra.ui.utils.PlayerNameRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constantes.FAVORITES_DIR)
@PreAuthorize(Constantes.HAS_ROLE_USER)
public class FavoritePlayersController {

    private final GestionJugadoresFavoritos gestionJugadoresFavoritos;

    public FavoritePlayersController(GestionJugadoresFavoritos gestionJugadoresFavoritos) {
        this.gestionJugadoresFavoritos = gestionJugadoresFavoritos;
    }

    @GetMapping
    public ResponseEntity<List<PlayerEntity>> getFavoritePlayers() {
        String username = getCurrentUsername();
        List<PlayerEntity> favoritePlayerEntities = gestionJugadoresFavoritos.getPlayersForUser(username);
        return ResponseEntity.status(HttpServletResponse.SC_OK).body(favoritePlayerEntities);
    }

    @GetMapping(Constantes.ID_ARGUMENT)
    public ResponseEntity<PlayerEntity> getFavoritePlayer(@PathVariable int id) {
        String username = getCurrentUsername();
        PlayerEntity playerEntity = gestionJugadoresFavoritos.getPlayerForUser(username, id);
        return ResponseEntity.status(HttpServletResponse.SC_OK).body(playerEntity);
    }

    @PostMapping
    public ResponseEntity<PlayerEntity> addFavoritePlayer(@RequestBody PlayerNameRequest request) {
        String username = getCurrentUsername();
        PlayerEntity addedPlayer = gestionJugadoresFavoritos.addPlayerForUser(username, request.getPlayerName());
        return ResponseEntity.status(HttpServletResponse.SC_CREATED).body(addedPlayer);
    }

    @DeleteMapping(Constantes.ID_ARGUMENT)
    public ResponseEntity<Void> deleteFavoritePlayer(@PathVariable int id) {
        String username = getCurrentUsername();
        gestionJugadoresFavoritos.deletePlayerForUser(username, id);
        return ResponseEntity.status(HttpServletResponse.SC_NO_CONTENT).build();
    }

    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}