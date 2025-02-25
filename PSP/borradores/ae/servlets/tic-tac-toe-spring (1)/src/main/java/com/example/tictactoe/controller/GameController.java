package com.example.tictactoe.controller;

import com.example.tictactoe.model.Game;
import com.example.tictactoe.service.GameService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class GameController {
    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @PostMapping("/start")
    public String startGame(@RequestParam String player1, @RequestParam String player2, HttpSession session) {
        Game game = gameService.startNewGame(player1, player2);
        session.setAttribute("game", game);
        return "redirect:/play";
    }

    @GetMapping("/play")
    public String playGame(HttpSession session, Model model) {
        Game game = (Game) session.getAttribute("game");
        if (game == null) {
            return "redirect:/";
        }
        model.addAttribute("game", game);
        return "play";
    }

    @PostMapping("/move")
    public String makeMove(@RequestParam int position, HttpSession session) {
        Game game = (Game) session.getAttribute("game");
        if (game != null) {
            gameService.makeMove(game, position);
        }
        return "redirect:/play";
    }

   

    @PostMapping("/reset")
    public String resetGame(HttpSession session) {
        session.removeAttribute("game");
        return "redirect:/";
    }
}

