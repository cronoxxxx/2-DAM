package org.example.loginspring_adriansaavedra.ui.controllers;

import org.example.loginspring_adriansaavedra.common.Constantes;
import org.example.loginspring_adriansaavedra.domain.model.Player;
import org.example.loginspring_adriansaavedra.domain.service.GestionJugadores;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/players")
public class PlayersController {

    private final GestionJugadores gestionJugadores;

    public PlayersController(GestionJugadores gestionJugadores) {
        this.gestionJugadores = gestionJugadores;
    }

    @GetMapping
    public String getPlayers(Model model) {
        model.addAttribute("players", gestionJugadores.getAllPlayers());
        return "players";
    }

    @GetMapping("/edit")
    public String editPlayer(@RequestParam String id, Model model) {
        Player player = gestionJugadores.getPlayerById(id);
        if (player != null) {
            model.addAttribute("player", player);
            return "update";
        }
        model.addAttribute("error", "Player not found");
        return "players";
    }

    @PostMapping
    public String handlePlayerAction(@RequestParam String action, @RequestParam(required = false) String id,
                                     @RequestParam(required = false) String name,
                                     @RequestParam(required = false) String team,
                                     @RequestParam(required = false) String country,
                                     Model model) {
        switch (action) {
            case Constantes.ADD:
                boolean added = gestionJugadores.addPlayer(Player.builder().name(name).team(team).country(country).build());
                if (!added) {
                    model.addAttribute(Constantes.ERROR_ADD_PLAYER, Constantes.MESSAGE_ERROR_ADD_PLAYER);
                }
                break;
            case Constantes.UPDATE:
                gestionJugadores.updatePlayer(new Player(Integer.parseInt(id), name, team, country));
                break;
            default:
                gestionJugadores.deletePlayer(id);
        }
        model.addAttribute("players", gestionJugadores.getAllPlayers());
        return "players";
    }
}