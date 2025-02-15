package org.example.loginspring_adriansaavedra.domain.validators;

import org.example.loginspring_adriansaavedra.common.errors.PlayerValidatorException;
import org.example.loginspring_adriansaavedra.domain.model.Player;
import org.springframework.stereotype.Component;

@Component
public class PlayerValidator {
    public boolean validatePlayer(Player player) {
        if (player.getName().isBlank() || player.getCountry().isBlank()) {
            throw new PlayerValidatorException("El jugador debe tener un nombre y un país");
        } else {
            return true;
        }
    }
}
