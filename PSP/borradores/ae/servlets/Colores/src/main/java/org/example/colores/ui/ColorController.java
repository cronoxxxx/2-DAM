package org.example.colores.ui;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.colores.common.Constantes;
import org.example.colores.domain.model.Credential;
import org.example.colores.domain.service.ServiceColor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor

@Controller
@RequestMapping("/color")

public class ColorController {
    private final ServiceColor serviceColor;

    @GetMapping
    public String handleColorRequest(@RequestParam(name = Constantes.GLOBAL_COLOR, required = false) String newGlobalColor,
                                     @RequestParam(name = Constantes.USER_COLOR, required = false) String newUserColor,
                                     HttpSession session,
                                     Model model) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/auth/login";
        }

        if (newGlobalColor != null && !newGlobalColor.isEmpty()) {
            serviceColor.setGlobalColor(newGlobalColor);
        }

        if (newUserColor != null && !newUserColor.isEmpty()) {
            serviceColor.changeUserColor(new Credential(username, null, newUserColor));
        }

        String globalColor = serviceColor.getGlobalColor();
        String userColor = serviceColor.getUserColor(username);

        model.addAttribute(Constantes.GLOBAL_COLOR, globalColor);
        model.addAttribute(Constantes.USER_COLOR, userColor);

        return "colors";
    }


//    @GetMapping
//    public String handleColorRequest(@RequestParam(name = Constantes.GLOBAL_COLOR, required = false) String newGlobalColor,
//                                     @RequestParam(name = Constantes.USER_COLOR, required = false) String newUserColor,
//                                     HttpSession session,
//                                     Model model) {
//        String globalColor = Constantes.PRED_COLOR;
//        String userColor = (String) session.getAttribute(Constantes.USER_COLOR);
//        if (userColor == null) {
//            userColor = Constantes.PRED_COLOR;
//        }
//
//        if (newGlobalColor != null && !newGlobalColor.isEmpty()) {
//            globalColor = newGlobalColor;
//        }
//
//        if (newUserColor != null && !newUserColor.isEmpty()) {
//            userColor = newUserColor;
//            session.setAttribute(Constantes.USER_COLOR, userColor);
//        }
//
//        model.addAttribute(Constantes.GLOBAL_COLOR, globalColor);
//        model.addAttribute(Constantes.USER_COLOR, userColor);
//
//        return "colors";
//    }


}