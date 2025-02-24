package org.example.loginspring_adriansaavedra.ui.controllers;

import jakarta.servlet.http.HttpSession;
import org.example.loginspring_adriansaavedra.common.Constantes;
import org.example.loginspring_adriansaavedra.domain.model.Credential;
import org.example.loginspring_adriansaavedra.domain.service.GestionCredenciales;
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

    @GetMapping(Constantes.LOGIN_DIR)
    public String loginPage() {
        return Constantes.LOGIN;
    }

    @PostMapping(Constantes.LOGIN_DIR)
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {
        if (gestionCredenciales.authenticateUser(username, password)) {
            session.setAttribute(Constantes.USER, username);
            return Constantes.REDIRECT + Constantes.PLAYERS_DIR;
        } else {
            model.addAttribute(Constantes.ERROR, Constantes.ERROR_MESSAGE);
            return Constantes.LOGIN;
        }
    }

    @GetMapping(Constantes.LOGOUT_DIR)
    public String logout(HttpSession session) {
        session.invalidate();
        return Constantes.REDIRECT + Constantes.LOGIN_DIR;
    }

    @GetMapping(Constantes.REGISTER_DIR)
    public String registerPage() {
        return Constantes.REGISTER;
    }

    @PostMapping(Constantes.REGISTER_DIR)
    public String register(@RequestParam String username, @RequestParam String password, @RequestParam String email, Model model) {
        Credential credential = Credential.builder()
                .username(username)
                .password(password)
                .email(email)
                .build();

        if (gestionCredenciales.registerUser(credential)) {
            model.addAttribute(Constantes.MESSAGE, Constantes.SUCCESS_REGISTER_MESSAGE);
            return Constantes.LOGIN;
        } else {
            model.addAttribute(Constantes.ERROR, Constantes.ERROR_REGISTER_MESSAGE);
            return Constantes.REGISTER;
        }
    }

    @GetMapping(Constantes.VERIFY_DIR)
    public String verifyUser(@RequestParam String code, Model model) {
        if (gestionCredenciales.verifyUser(code)) {
            model.addAttribute(Constantes.MESSAGE, Constantes.SUCCESS_MESSAGE);
        } else {
            model.addAttribute(Constantes.ERROR, Constantes.EXPIRED_CODE_MESSAGE);
        }
        return Constantes.LOGIN;
    }
}