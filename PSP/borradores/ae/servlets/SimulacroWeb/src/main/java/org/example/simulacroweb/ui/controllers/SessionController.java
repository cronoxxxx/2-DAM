package org.example.simulacroweb.ui.controllers;

import jakarta.servlet.http.HttpSession;
import org.example.simulacroweb.domain.model.User;
import org.example.simulacroweb.domain.services.ServiceUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class SessionController {

    @Autowired
    private ServiceUser serviceUser;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }

    @PostMapping("/register")
    public String registerSubmit(@RequestParam String username, @RequestParam String password, Model model) {
        User user = new User();
        user.setUsuario(username);
        user.setPassword(password);
        if (serviceUser.register(user)) {
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
    public String loginSubmit(@RequestParam String username, @RequestParam String password, HttpSession session) {
        if (serviceUser.authenticateUser(username, password)) {
            session.setAttribute("username", username);
            return "redirect:/products";
        } else {
            return "redirect:/login?error";
        }
    }
}