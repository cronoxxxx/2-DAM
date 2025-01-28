package org.example.loginspring_adriansaavedra.ui.controllers;

import jakarta.servlet.http.HttpSession;
import org.example.loginspring_adriansaavedra.common.Constantes;
import org.example.loginspring_adriansaavedra.domain.model.Credential;
import org.example.loginspring_adriansaavedra.domain.service.GestionCredenciales;
import org.example.loginspring_adriansaavedra.domain.service.GestionJugadores;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    private final GestionCredenciales gestionCredenciales;

    public LoginController(GestionCredenciales gestionCredenciales) {
        this.gestionCredenciales = gestionCredenciales;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {
        if (gestionCredenciales.authenticateUser(username, password)) {
            session.setAttribute("user", username);
            return "redirect:/players";
        } else {
            model.addAttribute("error", "Invalid username or password, or account not verified");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password, @RequestParam String email, Model model) {
        Credential credential = Credential.builder()
                .username(username)
                .password(password)
                .email(email)
                .build();

        if (gestionCredenciales.registerUser(credential)) {
            model.addAttribute("message", "Registration successful. Please check your email to verify your account.");
            return "login";
        } else {
            model.addAttribute("error", "Username already exists");
            return "register";
        }
    }

    @GetMapping("/verify")
    public String verifyUser(@RequestParam String code, Model model) {
        if (gestionCredenciales.verifyUser(code)) {
            model.addAttribute("message", "Your account has been verified. You can now log in.");
        } else {
            model.addAttribute("error", "Invalid or expired verification code.");
        }
        return "login";
    }
}