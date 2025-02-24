package com.example.tictactoe.controller;

import com.example.tictactoe.model.Game;
import com.example.tictactoe.model.User;
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

    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password, Model model) {
        if (gameService.registerUser(username, password)) {
            return "redirect:/login";
        } else {
            model.addAttribute("error", "Username already exists");
            return "register";
        }
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {
        User user = gameService.authenticateUser(username, password);
        if (user != null) {
            session.setAttribute("user", user);
            return "redirect:/game";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }

    @GetMapping("/game")
    public String gameForm(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        return "game";
    }

    @PostMapping("/game")
    public String startGame(@RequestParam String player1, @RequestParam String player2, HttpSession session) {
        Game game = gameService.startNewGame(player1, player2);
        session.setAttribute("game", game);
        return "redirect:/play";
    }

    @GetMapping("/play")
    public String playGame(HttpSession session, Model model) {
        Game game = (Game) session.getAttribute("game");
        if (game == null) {
            return "redirect:/game";
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

    @PostMapping("/random-move")
    public String makeRandomMove(HttpSession session) {
        Game game = (Game) session.getAttribute("game");
        if (game != null) {
            gameService.makeRandomMove(game);
        }
        return "redirect:/play";
    }

    @PostMapping("/reset")
    public String resetGame(HttpSession session) {
        Game game = (Game) session.getAttribute("game");
        if (game != null) {
            game = gameService.startNewGame(game.getPlayer1(), game.getPlayer2());
            session.setAttribute("game", game);
        }
        return "redirect:/play";
    }
}

