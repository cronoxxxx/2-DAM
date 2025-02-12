package org.example.loginspring_adriansaavedra.domain.validators;

import org.example.loginspring_adriansaavedra.domain.model.Player;
import org.springframework.stereotype.Component;

@Component
public class PlayerValidator {
    public boolean validatePlayer(Player player) {
        return player.getName().isBlank() || player.getCountry().isBlank();
    }
}
