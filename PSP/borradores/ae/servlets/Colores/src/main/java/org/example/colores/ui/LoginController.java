package org.example.colores.ui;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.colores.domain.model.Credential;
import org.example.colores.domain.service.ServiceCredenciales;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LoginController {

    private final ServiceCredenciales serviceCredenciales;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        Credential credential = new Credential(username, password);
        Credential loggedUser = serviceCredenciales.login(credential);
        if (loggedUser != null) {
            session.setAttribute("username", loggedUser.getUsername());
            session.setAttribute("userColor", loggedUser.getColor());
            return "redirect:/color";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           Model model) {
        Credential credential = new Credential(username, password);
        if (serviceCredenciales.register(credential)) {
            return "redirect:/auth/login";
        } else {
            model.addAttribute("error", "Username already exists");
            return "register";
        }
    }
}

