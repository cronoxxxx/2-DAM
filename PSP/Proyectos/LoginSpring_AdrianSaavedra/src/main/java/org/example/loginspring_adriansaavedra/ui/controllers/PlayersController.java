package org.example.loginspring_adriansaavedra.ui.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.example.loginspring_adriansaavedra.common.Constantes;
import org.example.loginspring_adriansaavedra.domain.model.Player;
import org.example.loginspring_adriansaavedra.domain.service.GestionJugadores;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<Player>> getPlayers() {
        return ResponseEntity.status(HttpServletResponse.SC_OK)
                .body(gestionJugadores.getAllPlayers());
    }

    @GetMapping(Constantes.ID_ARGUMENT)
    public ResponseEntity<Player> getPlayer(@PathVariable String id) {
        Player player = gestionJugadores.getPlayerById(id);

        return ResponseEntity.status(HttpServletResponse.SC_OK).body(player);


    }
}