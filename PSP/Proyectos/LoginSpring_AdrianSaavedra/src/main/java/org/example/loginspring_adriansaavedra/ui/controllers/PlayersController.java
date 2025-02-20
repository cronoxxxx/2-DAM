package org.example.loginspring_adriansaavedra.ui.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.example.loginspring_adriansaavedra.common.Constantes;
import org.example.loginspring_adriansaavedra.domain.model.PlayerEntity;
import org.example.loginspring_adriansaavedra.domain.service.GestionJugadores;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(Constantes.PLAYERS_DIR)
public class PlayersController {

    private final GestionJugadores gestionJugadores;

    public PlayersController(GestionJugadores gestionJugadores) {
        this.gestionJugadores = gestionJugadores;
    }

    @GetMapping
    public ResponseEntity<List<PlayerEntity>> getPlayers() {
        return ResponseEntity.status(HttpServletResponse.SC_OK)
                .body(gestionJugadores.getAllPlayers());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PlayerEntity> addPlayer(@RequestBody PlayerEntity playerEntity) {
        gestionJugadores.addPlayer(playerEntity);
        return ResponseEntity.status(HttpServletResponse.SC_CREATED).body(playerEntity);
    }

    @PutMapping(Constantes.ID_ARGUMENT)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PlayerEntity> updatePlayer(@PathVariable String id, @RequestBody PlayerEntity playerEntity) {
        playerEntity.setId(Integer.parseInt(id));
        gestionJugadores.updatePlayer(playerEntity);
        return ResponseEntity.status(HttpServletResponse.SC_OK).body(playerEntity);
    }

    @DeleteMapping(Constantes.ID_ARGUMENT)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletePlayer(@PathVariable String id) {
        gestionJugadores.deletePlayer(id);
        return ResponseEntity.status(HttpServletResponse.SC_NO_CONTENT).build();
    }

    @GetMapping(Constantes.ID_ARGUMENT)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PlayerEntity> getPlayer(@PathVariable String id) {
        PlayerEntity playerEntity = gestionJugadores.getPlayerById(id);
        return ResponseEntity.status(HttpServletResponse.SC_OK).body(playerEntity);


    }
}