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

    @PostMapping
    public ResponseEntity<Player> addPlayer(@RequestBody Player player) {
        gestionJugadores.addPlayer(player);

        return ResponseEntity.status(HttpServletResponse.SC_CREATED).body(player);

    }

    @PutMapping(Constantes.ID_ARGUMENT)
    public ResponseEntity<Player> updatePlayer(@PathVariable String id, @RequestBody Player player) {
        player.setId(Integer.parseInt(id));
        gestionJugadores.updatePlayer(player);
        return ResponseEntity.status(HttpServletResponse.SC_OK).body(player);
    }

    @DeleteMapping(Constantes.ID_ARGUMENT)
    public ResponseEntity<Void> deletePlayer(@PathVariable String id) {
        gestionJugadores.deletePlayer(id);
        return ResponseEntity.status(HttpServletResponse.SC_NO_CONTENT).build();


    }
    //validator throw
    //delete
    //manejo de token invalido 401 correcto
}